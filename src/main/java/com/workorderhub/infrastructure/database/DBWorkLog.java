package com.workorderhub.infrastructure.database;

import com.workorderhub.core.entity.*;
import com.workorderhub.core.gateway.WorkLogGateway;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBWorkLog implements WorkLogGateway {

    private Connection sqlManager;

    @Override
    public List<WorkLogElement> getWorkLogElementList(LocalDateTime startDate) {

        WorkLogElement workLogElement = null;
        List<WorkLogElement> workLogElementList = new ArrayList<>();

        String sql = """
                SELECT
                wl.log_id,
                wl.log_date,
                wl.log_comment,
                wl.work_order_id,
                em.employee_name,
                wo.work_start_date,
                wo.work_end_date,
                hl.employee_name,
                wp.work_permit_id
                FROM work_log wl
                LEFT JOIN work_order wo ON wl.work_order_id = wo.work_order_id
                LEFT JOIN work_permit wp on wl.work_permit_id = wp.work_permit_id
                lEFT JOIN employee em ON wl.employee_id = em.employee_id
                LEFT JOIN employee hl ON wo.holder = hl.employee_id
                WHERE wl.log_date > ?
                ORDER BY wl.log_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setObject(1, startDate);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                WorkLogInfo workLogInfo = new WorkLogInfo();
                workLogInfo.setLogId(resultSet.getInt("wl.log_id"));
                workLogInfo.setLogDate(resultSet.getObject("wl.log_date", LocalDateTime.class));
                workLogInfo.setLogComment(resultSet.getString("wl.log_comment"));
                workLogInfo.setWorkOrderId(resultSet.getLong("wl.work_order_id"));

                User user = new User();
                user.setUserName(resultSet.getString("em.employee_name"));

                WorkOrderInfo workOrderInfo = new WorkOrderInfo();
                workOrderInfo.setStartDate(
                        resultSet.getDate("wo.work_start_date") == null ?
                                null : resultSet.getDate("wo.work_start_date").toLocalDate()
                );
                workOrderInfo.setEndDate(
                        resultSet.getDate("wo.work_end_date") == null ?
                                null : resultSet.getDate("wo.work_end_date").toLocalDate()
                );

                User holder = new User();
                holder.setUserName(resultSet.getString("hl.employee_name"));

                WorkPermit workPermit = new WorkPermit();
                workPermit.setWorkPermitId(resultSet.getInt("wp.work_permit_id"));

                workLogElement = new WorkLogElement.Builder()
                        .withWorkLog(workLogInfo)
                        .withEmployee(user)
                        .withWorkOrderInfo(workOrderInfo)
                        .withHolder(holder)
                        .withWorkPermit(workPermit)
                        .build();

                workLogElementList.add(workLogElement);

            }

            resultSet.close();
            statement.close();

        }
        catch(Exception e) {
            System.out.println(e.getMessage());

        }
        finally {
            DBConnection.DBDisconnect();
        }
        return  workLogElementList;
    }

    @Override
    public List<WorkLogInfo> getWorkLogInfoList() {
        List<WorkLogInfo> workLogInfoList = new ArrayList<>();
        String sql = """
                SELECT *
                FROM work_log
                ORDER BY log_id;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            Statement statement = sqlManager.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                WorkLogInfo workLogInfo = new WorkLogInfo();
                workLogInfo.setLogId(resultSet.getInt("log_id"));
                workLogInfo.setLogDate(resultSet.getObject("log_date", LocalDateTime.class));
                workLogInfo.setLogComment(resultSet.getString("log_comment"));
                workLogInfo.setUserId(resultSet.getInt("employee_id"));
                workLogInfo.setWorkOrderId(resultSet.getLong("work_order_id"));
                workLogInfo.setWorkPermitId(resultSet.getInt("work_permit_id"));

                workLogInfoList.add(workLogInfo);

            }

            resultSet.close();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
        return workLogInfoList;
    }

    @Override
    public void insertWorkLogInfo(WorkLogInfo workLogInfo) {
        String sql = """
                INSERT INTO work_log
                (log_date, log_comment, employee_id, work_order_id, work_permit_id)
                VALUES (?,?,?,?,?);
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setObject(1, workLogInfo.getLogDate());
            statement.setString(2, workLogInfo.getLogComment());
            statement.setInt(3, workLogInfo.getUserId());
            statement.setLong(4, workLogInfo.getWorkOrderId());
            if (workLogInfo.getWorkPermitId() == 0) {
                statement.setNull(5, Types.NULL);
            } else {
                statement.setInt(5, workLogInfo.getWorkPermitId());
            }

            statement.executeUpdate();
            statement.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        } finally {
            DBConnection.DBDisconnect();

        }
    }

    @Override
    public boolean deleteWorkLogInfo(WorkLogInfo workLogInfo) {
        String sql = """
                DELETE FROM work_log
                WHERE log_id = ?;
                """;

        try {
            sqlManager = DBConnection.DBConnect();
            PreparedStatement statement = sqlManager.prepareStatement(sql);
            statement.setInt(1, workLogInfo.getLogId());

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
}
