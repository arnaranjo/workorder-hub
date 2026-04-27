package com.workorderhub.infrastructure.ui.technician;

import com.workorderhub.core.caseuse.technician.RequestWorkFront;
import com.workorderhub.core.caseuse.technician.TechnicianMainInput;
import com.workorderhub.core.caseuse.technician.TechnicianMainInteractor;
import com.workorderhub.core.caseuse.technician.TechnicianMainView;
import com.workorderhub.infrastructure.common.AppState;
import com.workorderhub.infrastructure.common.PropertiesLoader;
import com.workorderhub.infrastructure.common.Util;
import com.workorderhub.infrastructure.models.WorkFrontModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TechnicianMainController implements TechnicianMainView {

    private TechnicianMainInput interactor;

    @FXML
    private ChoiceBox<String> searchCriteriaSelector;
    @FXML
    private TextField workOrderSearchField;
    @FXML
    private Label mainLabel;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private TableView<WorkFrontModel> workFrontTable;

    private TableColumn<WorkFrontModel, Long> workOrderIdColumn;
    private TableColumn<WorkFrontModel, LocalDate> startDateColumn;
    private TableColumn<WorkFrontModel, LocalDate> endDateColumn;
    private TableColumn<WorkFrontModel, String> plantElementColumn;
    private TableColumn<WorkFrontModel, String> workProcedureColumn;
    private TableColumn<WorkFrontModel, String> holderColumn;
    private TableColumn<WorkFrontModel, String> statusColumn;

    private List<WorkFrontModel> workFrontList;
    private FilteredList<WorkFrontModel> workFrontFilList;
    private ObservableList<WorkFrontModel> workFrontObsList;

    //private StatusEnum onGoingStatus;
    //private StatusEnum closedStatus;
    //private WorkLogEnum startOrder;
    //private WorkLogEnum closeOrder;


    //private final URL workOrderReview = getClass().getResource("/com/orderhub/technician/workorder-re-view.fxml");

    public TechnicianMainController(TechnicianMainInteractor interactor) {
        this.interactor = interactor;
    }

    public void initialize() {
        mainLabel.setText(PropertiesLoader.GetText("technicianView.default"));
        mainLabel.setStyle(PropertiesLoader.GetText("technicianView.defaultStyle"));

        //onGoingStatus = StatusEnum.ONGOING;
        //closedStatus = StatusEnum.CLOSED;

        //startOrder = WorkLogEnum.START_ORDER;
        //closeOrder = WorkLogEnum.CLOSE_ORDER;

        workOrderIdColumn = new TableColumn<>(PropertiesLoader.GetText("technicianView.workOrderId"));
        workOrderIdColumn.setCellValueFactory(new PropertyValueFactory<>("workOrderId"));
        startDateColumn = new TableColumn<>(PropertiesLoader.GetText("technicianView.startDate"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn = new TableColumn<>(PropertiesLoader.GetText("technicianView.endDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        plantElementColumn = new TableColumn<>(PropertiesLoader.GetText("technicianView.plantElement"));
        plantElementColumn.setCellValueFactory(new PropertyValueFactory<>("plantElementTag"));
        workProcedureColumn = new TableColumn<>(PropertiesLoader.GetText("technicianView.workProcedureCode"));
        workProcedureColumn.setCellValueFactory(new PropertyValueFactory<>("workProcedureCode"));
        holderColumn = new TableColumn<>(PropertiesLoader.GetText("technicianView.holderName"));
        holderColumn.setCellValueFactory(new PropertyValueFactory<>("holderName"));
        statusColumn = new TableColumn<>(PropertiesLoader.GetText("technicianView.status"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        workFrontList = new ArrayList<>();
        interactor.retrieveWorkFrontList(
                new RequestWorkFront(AppState.getInstance().getLoggedUserId())
        );

        setWorkOrderInfo();
        setSearchFunction();

    }

    @FXML
    private void reviewWorkOrder() {
        if (!workFrontTable.getSelectionModel().isEmpty()){
            AppState.getInstance().setWorkOrderId(
                    workFrontTable.getSelectionModel().getSelectedItem().getWorkOrderId()
            );

            //LoadView

        }
        else {
            String errTitle = "technicianView.noSelectedTitle";
            String errMessage = "technicianView.noSelectedMessage";
            Util.ShowMessage(errTitle, errMessage);

        }
    }


    /**
     * Sets the function to display the work order description when is selected.
     */
    private void setWorkOrderInfo() {
        workFrontTable.getSelectionModel().selectedItemProperty().addListener((_, _, newSelection) -> {
            if (newSelection != null) {
                descriptionArea.setText(newSelection.getDescription());
            }
        });
    }

    /**
     * Sets the listener to filter the table and show the instrument or equipment.
     */
    private void setSearchFunction() {
        String[] list = PropertiesLoader.GetStringArray("technicianView.searchList");
        searchCriteriaSelector.getItems().addAll(list);
        searchCriteriaSelector.setValue(list[0]);

        workOrderSearchField.setPromptText(PropertiesLoader.GetText("technicianView.searchTag"));
        workOrderSearchField.textProperty().addListener((_, _, newValue) -> {
            switch (searchCriteriaSelector.getValue()) {
                case "ID":
                    workFrontFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }
                        return String.valueOf(p.getWorkOrderId()).contains(newValue);
                    });
                    break;
                case "Tag":
                    workFrontFilList.setPredicate(p ->
                            p.getPlantElementTag().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;
                case "Holder":
                    workFrontFilList.setPredicate(p ->
                            p.getHolderName().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;

                case "Start Date":
                    workFrontFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        String dateString = p.getStartDate() != null ?
                                p.getStartDate().format(formatter) : "";

                        return dateString.contains(newValue.trim());
                    });
                    break;

                case "End Date":
                    workFrontFilList.setPredicate(p -> {
                        if (newValue == null || newValue.trim().isEmpty()) {
                            return true;
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                        String dateString = p.getEndDate() != null ?
                                p.getEndDate().format(formatter) : "";

                        return dateString.contains(newValue.trim());
                    });
                    break;
            }
        });

        searchCriteriaSelector.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                workOrderSearchField.setText("");
            }
        });

    }

    /**
     * Sets the "on Going" status in the work order selected.
     * The action is logged in the database.
     */
    @FXML
    public void SetOnGoing() {

        /* Interactor to set on going status

        if (!workFrontTable.getSelectionModel().isEmpty()) {
            if (
                    !workFrontTable.getSelectionModel().getSelectedItem()
                            .getStatus().equals(onGoingStatus.GetStatus())
            ) {
                selectedWorkOrderInfo = workOrderInfoService.GetById(
                        workFrontTable.getSelectionModel().getSelectedItem().
                                getWorkOrder().getWorkOrderId()
                );
                selectedWorkOrderInfo.setStatusId(Util.GetStatusId(onGoingStatus));
                workOrderInfoService.Update(selectedWorkOrderInfo);

                workFrontTable.getSelectionModel().getSelectedItem()
                        .getCurrentStatus().setOrderStatus(onGoingStatus.GetStatus());
                workFrontTable.refresh();

                workLogInfoService.Insert(
                        startOrder.GetLogComment(),
                        workFrontTable.getSelectionModel().getSelectedItem().getWorkOrder(),
                        Util.getUserLogged().getId()
                );


            } else {
                String errTitle = "technicianView.errorTitle";
                String errMessage = "technicianView.errorMessage";
                Util.ShowMessage(errTitle, errMessage);
            }

        } else {
            String errTitle = "technicianView.noSelectedTitle";
            String errMessage = "technicianView.noSelectedMessage";
            Util.ShowMessage(errTitle, errMessage);

        }

            */
    }

    /**
     * Sets the "closed" status in the work order selected and remove it from the table.
     * The action is logged in the database.
     */
    @FXML
    public void SetClosed() {

        /* Interactor to set closed status

        if (!workFrontTable.getSelectionModel().isEmpty()) {

            String titleConf = "technicianView.closeTitle";
            String messageConf = "technicianView.closeMessage";

            if (Util.RequestConfirmation(titleConf, messageConf)) {
                selectedWorkOrderInfo = workOrderInfoService.GetById(
                        workFrontTable.getSelectionModel().getSelectedItem().getWorkOrder().getWorkOrderId()
                );
                selectedWorkOrderInfo.setStatusId(Util.GetStatusId(closedStatus));
                workOrderInfoService.Update(selectedWorkOrderInfo);

                workFrontObsList.remove(workFrontTable.getSelectionModel().getSelectedItem());
                workFrontTable.refresh();

                workLogInfoService.Insert(
                        closeOrder.GetLogComment(),
                        workFrontTable.getSelectionModel().getSelectedItem().getWorkOrder(),
                        Util.getUserLogged().getId()
                );

            }
        } else {
            String errTitle = "technicianView.errorTitle";
            String errMessage = "technicianView.errorMessage";
            Util.ShowMessage(errTitle, errMessage);

        }
         */

    }

    @Override
    public void setWorkFrontList(List<WorkFrontModel> workFrontList) {
        workFrontObsList = FXCollections.observableArrayList(workFrontList);
        workFrontFilList = new FilteredList<>(workFrontObsList);

        workFrontTable.setItems(workFrontFilList);
        workFrontTable.getColumns().add(workOrderIdColumn);
        workFrontTable.getColumns().add(startDateColumn);
        workFrontTable.getColumns().add(endDateColumn);
        workFrontTable.getColumns().add(workProcedureColumn);
        workFrontTable.getColumns().add(plantElementColumn);
        workFrontTable.getColumns().add(holderColumn);
        workFrontTable.getColumns().add(statusColumn);
    }
}
