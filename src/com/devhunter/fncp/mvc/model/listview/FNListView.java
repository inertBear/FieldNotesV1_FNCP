/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.listview;

import com.devhunter.fncp.constants.FNSqlConstants;
import com.devhunter.fncp.mvc.controller.sql.FNDataController;
import com.devhunter.fncp.mvc.controller.sql.billing.statemachine.FNBillingStateMachine;
import com.devhunter.fncp.mvc.model.FieldNote;
import com.devhunter.fncp.utilities.FNUtil;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FNListView extends JFXPanel {

    private ListView<FNDataPreview> mListView;

    public FNListView() {

        mListView = new ListView<>();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // set ListView size
                mListView.setPrefWidth(750);
                mListView.setPrefHeight(750);

                // create scene for JFXPanel
                Group root = new Group();
                Scene scene = new Scene(root, 750, 350);

                // add ListView to JFXPanel
                ((Group) scene.getRoot()).getChildren().add(mListView);
                setScene(scene);
            }
        });

        // when a ListView item is clicked
        mListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //get FieldNote by ticket number
                String ticketNumber = mListView.getSelectionModel().getSelectedItem().getTicketNumber();
                FNDataController con = new FNDataController();
                final FieldNote fieldNote = con.searchDataByTicketNumber(ticketNumber);

                //create Dialog
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Ticket Number: " + ticketNumber);
                dialogStage.initModality(Modality.WINDOW_MODAL);

                Button btnChangeState = new Button(FNBillingStateMachine.getInstance().getNextState(fieldNote.getBillingState()));
                btnChangeState.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        FNBillingStateMachine.getInstance().advanceState(fieldNote);
                        dialogStage.close();
                    }
                });

                Button btnCancel = new Button("Cancel");
                btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        dialogStage.close();
                    }
                });

                //create Vbox (a Vbox is a single vertical column)
                VBox vbox;
                if (!fieldNote.getBillingState().equals(FNSqlConstants.BILLING_STATE_COMPLETE)) {
                    vbox = new VBox(new Text(FNUtil.getFieldNoteAsString(fieldNote)), btnChangeState, btnCancel);
                } else {
                    vbox = new VBox(new Text(FNUtil.getFieldNoteAsString(fieldNote)), btnCancel);
                }
                vbox.setAlignment(Pos.CENTER);
                vbox.setPadding(new Insets(15));

                //set the scene to the dialog
                dialogStage.setScene(new Scene(vbox));
                dialogStage.show();
            }
        });

        // define the structure of the cell
        mListView.setCellFactory(param -> new ListCell<FNDataPreview>() {
            @Override
            protected void updateItem(FNDataPreview preview, boolean empty) {
                super.updateItem(preview, empty);

                if (empty || preview == null || preview.getTicketNumber() == null) {
                    setText(null);
                } else {
                    setText("Ticket Number: " + preview.getTicketNumber() + "   ||   Project: " + preview.getProject());
                }
            }
        });
    }

    public void addItems(ArrayList<FieldNote> fieldNotes) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (FieldNote each : fieldNotes) {
                    FNDataPreview listItem = new FNDataPreview(each);
                    mListView.getItems().add(listItem);
                }
            }
        });
    }

    public void removeItems() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mListView.getItems().clear();
            }
        });
    }
}
