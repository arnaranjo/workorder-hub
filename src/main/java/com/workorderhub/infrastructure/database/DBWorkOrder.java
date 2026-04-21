package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.*;
import com.workorderhub.core.gateway.WorkOrderGateway;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBWorkOrder implements WorkOrderGateway {

    private Connection sqlManager;

    @Override
    public List<WorkOrderInfo> getWorkOrderList() {

        List<WorkOrderInfo> workOrderInfoList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM work_order
                ORDER BY work_order_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                WorkOrderInfo workOrderInfo = new WorkOrderInfo();
                workOrderInfo.setWorkOrderId(resultSet.getLong("work_order_id"));
                workOrderInfo.setDescription(resultSet.getString("work_description"));
                workOrderInfo.setComments(resultSet.getString("work_comments"));
                if (resultSet.getDate("work_start_date") == null) {
                    workOrderInfo.setStartDate(null);
                } else {
                    workOrderInfo.setStartDate(resultSet.getDate("work_start_date").toLocalDate());
                }
                if (resultSet.getDate("work_end_date") == null) {
                    workOrderInfo.setEndDate(null);
                } else {
                    workOrderInfo.setEndDate(resultSet.getDate("work_end_date").toLocalDate());
                }
                workOrderInfo.setHolderId(resultSet.getInt("holder"));
                workOrderInfo.setStatusId(resultSet.getInt("status_id"));
                workOrderInfo.setPlantElementId(resultSet.getInt("plant_element_id"));
                workOrderInfo.setWorkProcedureId(resultSet.getInt("work_procedure_id"));
                workOrderInfo.setWorkPermitId(resultSet.getInt("work_permit_id"));

                workOrderInfoList.add(workOrderInfo);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return workOrderInfoList;
    }

    @Override
    public List<WorkOrderElement> getWorkFrontList() {

        List<WorkOrderElement> workFrontList = new ArrayList<>();
        String sql = """
                SELECT
                	wo.work_order_id,
                	wo.work_description,
                    wo.work_start_date,
                    wo.work_end_date,
                    pe.element_tag,
                    wpr.work_procedure_code,
                    wpt.lockout_device_id,
                    lp.loto_procedure_code,
                    hl.employee_name,
                    s.order_status
                FROM work_order wo
                LEFT JOIN employee hl ON wo.holder = hl.employee_id
                LEFT JOIN plant_element pe ON wo.plant_element_id = pe.element_id
                LEFT JOIN work_procedure wpr ON wo.work_procedure_id = wpr.work_procedure_id
                LEFT JOIN work_permit wpt ON wo.work_permit_id = wpt.work_permit_id
                LEFT JOIN loto_procedure lp ON wpt.loto_procedure_id = lp.loto_procedure_id
                LEFT JOIN status s ON wo.status_id = s.status_id
                WHERE s.order_status IN ('Open', 'On Going')
                ORDER BY wo.work_order_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                WorkOrderInfo workOrderInfo = new WorkOrderInfo();
                workOrderInfo.setWorkOrderId(resultSet.getLong("wo.work_order_id"));
                workOrderInfo.setDescription(resultSet.getString("wo.work_description"));
                workOrderInfo.setStartDate(
                        resultSet.getDate("wo.work_start_date") == null ?
                                null : resultSet.getDate("wo.work_start_date").toLocalDate()
                );
                workOrderInfo.setEndDate(
                        resultSet.getDate("wo.work_end_date") == null ?
                                null : resultSet.getDate("wo.work_end_date").toLocalDate()
                );

                PlantElement plantElement = new PlantElement.Builder()
                        .withElementId(0)
                        .withElementTag(resultSet.getString("pe.element_tag"))
                        .withElementDescription(null)
                        .withElementLocation(null)
                        .withInspectionDate(null)
                        .withInspectionFrequency(0)
                        .build();

                WorkProcedure workProcedure = new WorkProcedure();
                // Work procedure Code may be null.
                workProcedure.setDocumentCode(resultSet.getString("wpr.work_procedure_code"));

                WorkPermit workPermit = new WorkPermit();
                // LockoutDeviceId may be null.
                workPermit.setLockoutDeviceId(resultSet.getString("wpt.lockout_device_id"));

                LotoProcedure lotoProcedure = new LotoProcedure();
                lotoProcedure.setDocumentCode(resultSet.getString("lp.loto_procedure_code"));

                User user = new User();
                user.setUserName(resultSet.getString("hl.employee_name"));

                Status status = new Status();
                status.setOrderStatus(resultSet.getString("s.order_status"));

                WorkOrderElement workOrderElement = new WorkOrderElement.Builder()
                        .withWorkOrder(workOrderInfo)
                        .withPlantElement(plantElement)
                        .withEmployee(user)
                        .withWorkProcedure(workProcedure)
                        .withWorkPermit(workPermit)
                        .withLotoProcedure(lotoProcedure)
                        .withStatus(status)
                        .build();

                workFrontList.add(workOrderElement);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DBConnection.DBDisconnect();
        }

        return workFrontList;
    }

    @Override
    public List<WorkOrderElement> getAssignedWorkList(int employeeId) {

        List<WorkOrderElement> workFrontList = new ArrayList<>();
        String sql = """
                SELECT
                	wo.work_order_id,
                	wo.work_description,
                    wo.work_start_date,
                    wo.work_end_date,
                    pe.element_tag,
                    wpr.work_procedure_code,
                    wpt.lockout_device_id,
                    lp.loto_procedure_code,
                    hl.employee_name,
                    s.order_status
                FROM work_order wo
                LEFT JOIN employee hl ON wo.holder = hl.employee_id
                LEFT JOIN plant_element pe ON wo.plant_element_id = pe.element_id
                LEFT JOIN work_procedure wpr ON wo.work_procedure_id = wpr.work_procedure_id
                LEFT JOIN work_permit wpt ON wo.work_permit_id = wpt.work_permit_id
                LEFT JOIN loto_procedure lp ON wpt.loto_procedure_id = lp.loto_procedure_id
                LEFT JOIN status s ON wo.status_id = s.status_id
                INNER JOIN work_order_employee woe ON wo.work_order_id = woe.work_order_id
                WHERE s.order_status IN ('Open', 'On Going') AND woe.employee_id = ?
                ORDER BY wo.work_order_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, employeeId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                WorkOrderInfo workOrderInfo = new WorkOrderInfo();
                workOrderInfo.setWorkOrderId(resultSet.getLong("wo.work_order_id"));
                workOrderInfo.setDescription(resultSet.getString("wo.work_description"));
                workOrderInfo.setStartDate(
                        resultSet.getDate("wo.work_start_date") == null ?
                                null : resultSet.getDate("wo.work_start_date").toLocalDate()
                );
                workOrderInfo.setEndDate(
                        resultSet.getDate("wo.work_end_date") == null ?
                                null : resultSet.getDate("wo.work_end_date").toLocalDate()
                );

                PlantElement plantElement = new PlantElement.Builder()
                        .withElementId(0)
                        .withElementTag(resultSet.getString("pe.element_tag"))
                        .withElementDescription(null)
                        .withElementLocation(null)
                        .withInspectionDate(null)
                        .withInspectionFrequency(0)
                        .build();

                WorkProcedure workProcedure = new WorkProcedure();
                // Work procedure Code may be null.
                workProcedure.setDocumentCode(resultSet.getString("wpr.work_procedure_code"));

                WorkPermit workPermit = new WorkPermit();
                // LockoutDeviceId may be null.
                workPermit.setLockoutDeviceId(resultSet.getString("wpt.lockout_device_id"));

                LotoProcedure lotoProcedure = new LotoProcedure();
                lotoProcedure.setDocumentCode(resultSet.getString("lp.loto_procedure_code"));

                User user = new User();
                user.setUserName(resultSet.getString("hl.employee_name"));

                Status status = new Status();
                status.setOrderStatus(resultSet.getString("s.order_status"));

                WorkOrderElement workOrderElement = new WorkOrderElement.Builder()
                        .withWorkOrder(workOrderInfo)
                        .withPlantElement(plantElement)
                        .withEmployee(user)
                        .withWorkProcedure(workProcedure)
                        .withWorkPermit(workPermit)
                        .withLotoProcedure(lotoProcedure)
                        .withStatus(status)
                        .build();

                workFrontList.add(workOrderElement);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DBConnection.DBDisconnect();
        }

        return workFrontList;
    }

    @Override
    public List<WorkOrderElement> getClosedWorkList(LocalDate startDate) {

        List<WorkOrderElement> workFrontList = new ArrayList<>();
        String sql = getSql(startDate);

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);

            if (startDate != null) {
                statement.setDate(1, Date.valueOf(startDate));
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                WorkOrderInfo workOrderInfo = new WorkOrderInfo();
                workOrderInfo.setWorkOrderId(resultSet.getLong("wo.work_order_id"));
                workOrderInfo.setDescription(resultSet.getString("wo.work_description"));
                workOrderInfo.setStartDate(
                        resultSet.getDate("wo.work_start_date") == null ?
                                null : resultSet.getDate("wo.work_start_date").toLocalDate()
                );
                workOrderInfo.setEndDate(
                        resultSet.getDate("wo.work_end_date") == null ?
                                null : resultSet.getDate("wo.work_end_date").toLocalDate()
                );

                PlantElement plantElement = new PlantElement.Builder()
                        .withElementId(0)
                        .withElementTag(resultSet.getString("pe.element_tag"))
                        .withElementDescription(null)
                        .withElementLocation(null)
                        .withInspectionDate(null)
                        .withInspectionFrequency(0)
                        .build();

                WorkProcedure workProcedure = new WorkProcedure();
                // WorkProcedureCode may be null.
                workProcedure.setDocumentCode(resultSet.getString("wpr.work_procedure_code"));

                WorkPermit workPermit = new WorkPermit();
                // LockoutDeviceId may be null.
                workPermit.setLockoutDeviceId(resultSet.getString("wpt.lockout_device_id"));

                LotoProcedure lotoProcedure = new LotoProcedure();
                lotoProcedure.setDocumentCode(resultSet.getString("lp.loto_procedure_code"));

                User user = new User();
                user.setUserName(resultSet.getString("hl.employee_name"));

                Status status = new Status();
                status.setOrderStatus(resultSet.getString("s.order_status"));

                WorkOrderElement workOrderElement = new WorkOrderElement.Builder()
                        .withWorkOrder(workOrderInfo)
                        .withPlantElement(plantElement)
                        .withEmployee(user)
                        .withWorkProcedure(workProcedure)
                        .withWorkPermit(workPermit)
                        .withLotoProcedure(lotoProcedure)
                        .withStatus(status)
                        .build();

                workFrontList.add(workOrderElement);
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DBConnection.DBDisconnect();
        }

        return workFrontList;
    }

    @Override
    public WorkOrderElement getWorkFrontElement(long workOrderId) {

        WorkOrderElement workOrderElement = null;
        String sql = """
                SELECT
                wo.work_order_id,
                wo.work_description,
                wo.work_start_date,
                wo.work_end_date,
                wo.work_comments,
                pe.element_tag,
                pe.element_description,
                pe.element_location,
                wpr.work_procedure_id,
                wpr.work_procedure_code,
                wpr.work_procedure_name,
                hl.employee_name,
                hl.company_email,
                hl.phone_number,
                wpe.work_permit_id,
                wpe.work_permit_description,
                wpe.lockout_device_id,
                lp.loto_procedure_id,
                lp.loto_procedure_code,
                lp.loto_procedure_name,
                s.order_status
                FROM work_order wo
                LEFT JOIN employee hl ON wo.holder = hl.employee_id
                LEFT JOIN plant_element pe ON wo.plant_element_id = pe.element_id
                LEFT JOIN work_procedure wpr ON wo.work_procedure_id = wpr.work_procedure_id
                LEFT JOIN work_permit wpe ON wo.work_permit_id = wpe.work_permit_id
                LEFT JOIN loto_procedure lp ON wpe.loto_procedure_id = lp.loto_procedure_id
                LEFT JOIN status s ON wo.status_id = s.status_id
                WHERE wo.work_order_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, workOrderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                WorkOrderInfo workOrderInfo = new WorkOrderInfo();
                workOrderInfo.setWorkOrderId(resultSet.getLong("wo.work_order_id"));
                workOrderInfo.setDescription(resultSet.getString("wo.work_description"));
                workOrderInfo.setStartDate(
                        resultSet.getDate("wo.work_start_date") == null ?
                                null : resultSet.getDate("wo.work_start_date").toLocalDate()
                );
                workOrderInfo.setEndDate(
                        resultSet.getDate("wo.work_end_date") == null ?
                                null : resultSet.getDate("wo.work_end_date").toLocalDate()
                );
                workOrderInfo.setComments(resultSet.getString("wo.work_comments"));

                //The information of the plant element is not complete in this query,
                //It is set the missing information with default values.
                PlantElement plantElement = new PlantElement.Builder()
                        .withElementId(0)
                        .withElementTag(resultSet.getString("pe.element_tag"))
                        .withElementDescription(resultSet.getString("pe.element_description"))
                        .withElementLocation(resultSet.getString("pe.element_description"))
                        .withInspectionDate(null)
                        .withInspectionFrequency(0)
                        .build();

                User user = new User();
                user.setUserName(resultSet.getString("hl.employee_name"));
                user.setUserEmail(resultSet.getString("hl.company_email"));
                user.setUserPhoneNumber(resultSet.getString("hl.phone_number"));

                WorkProcedure workProcedure = new WorkProcedure();
                WorkPermit workPermit = new WorkPermit();
                LotoProcedure lotoProcedure = new LotoProcedure();
                if (resultSet.getInt("wpr.work_procedure_id") != 0) {
                    workProcedure.setDocumentCode(resultSet.getString("wpr.work_procedure_code"));
                    workProcedure.setDocumentCode(resultSet.getString("wpr.work_procedure_name"));
                } else {
                    workProcedure = null;
                }
                if (resultSet.getInt("wpe.work_permit_id") != 0) {
                    workPermit.setDescription(resultSet.getString("wpe.work_permit_description"));
                    workPermit.setLockoutDeviceId(resultSet.getString("wpe.lockout_device_id"));
                } else {
                    workPermit = null;
                }
                if (resultSet.getInt("loto_procedure_id") != 0) {
                    lotoProcedure.setDocumentCode(resultSet.getString("lp.loto_procedure_code"));
                    lotoProcedure.setDocumentName(resultSet.getString("lp.loto_procedure_name"));
                } else {
                    lotoProcedure = null;
                }

                Status status = new Status();
                status.setOrderStatus(resultSet.getString("s.order_status"));

                workOrderElement = new WorkOrderElement.Builder()
                        .withWorkOrder(workOrderInfo)
                        .withPlantElement(plantElement)
                        .withEmployee(user)
                        .withWorkProcedure(workProcedure)
                        .withWorkPermit(workPermit)
                        .withLotoProcedure(lotoProcedure)
                        .withStatus(status)
                        .build();
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            DBConnection.DBDisconnect();
        }

        return workOrderElement;
    }

    @Override
    public WorkOrderInfo getWorkOrderById(long workOrderId) {

        WorkOrderInfo workOrderInfo = null;
        String sql = """
                SELECT *
                FROM work_order
                WHERE work_order_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, workOrderId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                workOrderInfo = new WorkOrderInfo();
                workOrderInfo.setWorkOrderId(resultSet.getLong("work_order_id"));
                workOrderInfo.setDescription(resultSet.getString("work_description"));
                workOrderInfo.setComments(resultSet.getString("work_comments"));
                if (resultSet.getDate("work_start_date") == null) {
                    workOrderInfo.setStartDate(null);
                } else {
                    workOrderInfo.setStartDate(resultSet.getDate("work_start_date").toLocalDate());
                }
                if (resultSet.getDate("work_end_date") == null) {
                    workOrderInfo.setEndDate(null);
                } else {
                    workOrderInfo.setEndDate(resultSet.getDate("work_end_date").toLocalDate());
                }
                workOrderInfo.setHolderId(resultSet.getInt("holder"));
                workOrderInfo.setStatusId(resultSet.getInt("status_id"));
                workOrderInfo.setPlantElementId(resultSet.getInt("plant_element_id"));
                workOrderInfo.setWorkProcedureId(resultSet.getInt("work_procedure_id"));
                workOrderInfo.setWorkPermitId(resultSet.getInt("work_permit_id"));
            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return workOrderInfo;
    }

    @Override
    public boolean insertWorkOrder(WorkOrderInfo workOrderInfo) {

        boolean result = false;
        String sql = """
                INSERT INTO work_order
                (
                work_order_id,
                work_description,
                work_comments,
                work_start_date,
                work_end_date,
                holder,
                status_id,
                plant_element_id,
                work_procedure_id,
                work_permit_id
                )
                VALUES (?,?,?,?,?,?,?,?,?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);

            statement.setLong(1, workOrderInfo.getWorkOrderId());
            statement.setString(2, workOrderInfo.getDescription());
            if (workOrderInfo.getComments() == null) {
                statement.setNull(3, Types.NULL);
            } else {
                statement.setString(3, workOrderInfo.getComments());
            }
            if (workOrderInfo.getStartDate() == null) {
                statement.setNull(4, Types.NULL);
            } else {
                statement.setDate(4, Date.valueOf(workOrderInfo.getStartDate()));
            }
            if (workOrderInfo.getEndDate() == null) {
                statement.setNull(5, Types.NULL);
            } else {
                statement.setDate(5, Date.valueOf(workOrderInfo.getEndDate()));
            }
            statement.setInt(6, workOrderInfo.getHolderId());
            statement.setInt(7, workOrderInfo.getStatusId());
            statement.setInt(8, workOrderInfo.getPlantElementId());
            if (workOrderInfo.getWorkProcedureId() == 0) {
                statement.setNull(9, Types.NULL);
            } else {
                statement.setInt(9, workOrderInfo.getWorkProcedureId());
            }
            if (workOrderInfo.getWorkPermitId() == 0) {
                statement.setNull(10, Types.NULL);
            } else {
                statement.setInt(10, workOrderInfo.getWorkPermitId());
            }

            result = statement.executeUpdate() != 0;
            statement.close();


        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return result;
    }

    @Override
    public boolean deleteWorkOrder(WorkOrderInfo workOrderInfo) {

        String sql = """
                DELETE FROM work_order
                WHERE work_order_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setLong(1, workOrderInfo.getWorkOrderId());

            statement.executeUpdate();
            statement.close();
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return false;
    }

    @Override
    public boolean updateWorkOrder(WorkOrderInfo workOrderInfo) {

        String sql = """
                UPDATE work_order
                SET
                work_description = ?,
                work_comments = ?,
                work_start_date = ?,
                work_end_date = ?,
                holder = ?,
                status_id = ?,
                plant_element_id = ?,
                work_procedure_id = ?,
                work_permit_id  = ?
                WHERE work_order_id = ?;
                """;
        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);

            statement.setString(1, workOrderInfo.getDescription());
            if (workOrderInfo.getComments() == null) {
                statement.setNull(2, Types.NULL);
            } else {
                statement.setString(2, workOrderInfo.getComments());
            }
            if (workOrderInfo.getStartDate() == null) {
                statement.setNull(3, Types.NULL);
            } else {
                statement.setDate(3, Date.valueOf(workOrderInfo.getStartDate()));
            }
            if (workOrderInfo.getEndDate() == null) {
                statement.setNull(4, Types.NULL);
            } else {
                statement.setDate(4, Date.valueOf(workOrderInfo.getEndDate()));
            }
            statement.setInt(5, workOrderInfo.getHolderId());
            statement.setInt(6, workOrderInfo.getStatusId());
            statement.setInt(7, workOrderInfo.getPlantElementId());
            if (workOrderInfo.getWorkProcedureId() == 0) {
                statement.setNull(8, Types.NULL);
            } else {
                statement.setInt(8, workOrderInfo.getWorkProcedureId());
            }
            if (workOrderInfo.getWorkPermitId() == 0) {
                statement.setNull(9, Types.NULL);
            } else {
                statement.setInt(9, workOrderInfo.getWorkPermitId());
            }
            statement.setLong(10, workOrderInfo.getWorkOrderId());

            statement.executeUpdate();
            statement.close();
            return true;

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return false;
    }

    /**
     * Gets the SQL script according to the dates.
     *
     * @param startDate work order start date, it may be null.
     * @return complete sql script
     */
    private static String getSql(LocalDate startDate) {
        String sql = """
                SELECT
                    wo.work_order_id,
                    wo.work_description,
                    wo.work_start_date,
                    wo.work_end_date,
                    pe.element_tag,
                    wpr.work_procedure_code,
                    wpt.lockout_device_id,
                    lp.loto_procedure_code,
                    hl.employee_name,
                    s.order_status
                    FROM work_order wo
                LEFT JOIN employee hl ON wo.holder = hl.employee_id
                LEFT JOIN plant_element pe ON wo.plant_element_id = pe.element_id
                LEFT JOIN work_procedure wpr ON wo.work_procedure_id = wpr.work_procedure_id
                LEFT JOIN work_permit wpt ON wo.work_permit_id = wpt.work_permit_id
                LEFT JOIN loto_procedure lp ON wpt.loto_procedure_id = lp.loto_procedure_id
                LEFT JOIN status s ON wo.status_id = s.status_id
                WHERE s.order_status = 'Closed'
                """;

        if ((startDate != null)) {
            sql += " AND wo.work_start_date >= ? ";
        }

        sql += " ORDER BY wo.work_order_id;";

        return sql;
    }
}
