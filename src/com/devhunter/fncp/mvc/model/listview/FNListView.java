/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.listview;

import com.devhunter.fncp.constants.FNPConstants;
import com.devhunter.fncp.mvc.controller.FNDataController;
import com.devhunter.fncp.mvc.controller.billingStateMachine.FNBillingStateMachine;
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
import org.json.JSONArray;
import org.json.JSONObject;

import static com.devhunter.fncp.constants.FNCPConstants.*;
import static com.devhunter.fncp.constants.FNPConstants.*;

/**
 * embedded Java FX ListView in a Swing application. All calls to this ListView must be done within a <i>Platform.runLAter(...)</i>
 * block. For reference, this works a lot like an Android ListView.
 */
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

                JSONObject searchResponse = FNDataController.searchFieldNotes(null, null, null, ticketNumber);
                String status = searchResponse.getString(RESPONSE_STATUS_TAG);
                String messageString = searchResponse.getString(RESPONSE_MESSAGE_TAG);

                if (status.equals(RESPONSE_STATUS_SUCCESS)) {
                    // get the result
                    JSONArray messageArray = new JSONArray(messageString);
                    JSONObject message = messageArray.getJSONObject(0);

                    FieldNote fieldNote = FNUtil.buildFieldNote(message);

                    //create Dialog
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle(FN_TICKET_NUMBER_LABEL + " " + ticketNumber);
                    dialogStage.initModality(Modality.WINDOW_MODAL);

                    //create the "Advance State" button
                    Button btnChangeState = new Button(FNBillingStateMachine.getInstance()
                            .getNextState(fieldNote.getBillingState()));

                    btnChangeState.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            // advance the state of the FieldNote
                            FNBillingStateMachine.getInstance().advanceState(fieldNote);
                            dialogStage.close();
                            removeItem(mListView.getSelectionModel().getSelectedItem());
                        }
                    });


                    //Create the "Cancel/Close" button
                    Button btnCancel;
                    if (fieldNote.getBillingState().equals(FNPConstants.BILLING_STATE_COMPLETE)) {
                        btnCancel = new Button(BUTTON_CANCEL);
                    } else {
                        btnCancel = new Button(BUTTON_CLOSE);
                    }
                    btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            dialogStage.close();
                        }
                    });


                    //create Vbox  NOTE:(a Vbox is just a simple, single vertical column, this may need to be refined)
                    VBox vbox;
                    if (!fieldNote.getBillingState().equals(FNPConstants.BILLING_STATE_COMPLETE)) {
                        vbox = new VBox(5.0, new Text(FNUtil.getFieldNoteAsString(fieldNote)), btnChangeState, btnCancel);
                    } else {
                        vbox = new VBox(5.0, new Text(FNUtil.getFieldNoteAsString(fieldNote)), btnCancel);
                    }
                    vbox.setAlignment(Pos.CENTER);
                    vbox.setPadding(new Insets(15));

                    //set the scene to the dialog
                    dialogStage.setScene(new Scene(vbox));
                    dialogStage.show();
                }
            }
        });

        // define the structure of the "preview" cells
        mListView.setCellFactory(param -> new ListCell<FNDataPreview>() {
            @Override
            protected void updateItem(FNDataPreview preview, boolean empty) {
                super.updateItem(preview, empty);

                if (empty || preview == null || preview.getTicketNumber() == null) {
                    setText(null);
                } else {
                    setText(FN_TICKET_NUMBER_LABEL + " " + preview.getTicketNumber() + "   ||   " +
                            FN_PROJECT_LABEL + " " + preview.getProject());
                }
            }
        });
    }

    /**
     * Add a fieldnote to the ListView
     *
     * @param fieldNote to add to ListView
     */
    public void addItem(FieldNote fieldNote) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FNDataPreview listItem = new FNDataPreview(fieldNote);
                mListView.getItems().add(listItem);

            }
        });
    }

    /**
     * Remove an item from the ListView
     *
     * @param item
     */
    private void removeItem(FNDataPreview item) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mListView.getItems().remove(item);
            }
        });
    }

    /**
     * remove ALL items from the ListView
     */
    public void removeItems() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                mListView.getItems().clear();
            }
        });
    }
}
