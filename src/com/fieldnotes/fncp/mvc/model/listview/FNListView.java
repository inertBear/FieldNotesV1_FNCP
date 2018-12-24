/**
 * ? 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.fieldnotes.fncp.mvc.model.listview;

import com.fieldnotes.fncp.constants.FNPConstants;
import com.fieldnotes.fncp.mvc.controller.billingStateMachine.FNBillingStateMachine;
import com.fieldnotes.fncp.mvc.model.FNNote;
import com.fieldnotes.fncp.mvc.model.FNUser;
import com.fieldnotes.fncp.mvc.model.FieldNote;
import com.fieldnotes.fncp.utilities.FNUtil;
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

import static com.fieldnotes.fncp.constants.FNCPConstants.*;

/**
 * embedded Java FX ListView in a Swing application. All calls to this ListView must be done within a <i>Platform.runLAter(...)</i>
 * block. For reference, this works a lot like an Android ListView.
 */
public class FNListView extends JFXPanel {

    private ListView<FieldNote> mListView;
    private ArrayList<FieldNote> mList = new ArrayList<>();
    private boolean mIsBilling;

    public FNListView(boolean isBilling) {

        mListView = new ListView<>();
        mIsBilling = isBilling;

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
                // get index of selected item
                FieldNote note  = mListView.getSelectionModel().getSelectedItem();

                //create Dialog
                Stage dialogStage = new Stage();
                dialogStage.setTitle(FN_NOTE_NUMBER_LABEL + " " + note.getId());
                dialogStage.initModality(Modality.WINDOW_MODAL);

                VBox vbox = null;

                // if displaying a User
                if (note instanceof FNUser) {
                    FNUser fnUser = (FNUser) note;

                    //Create the "Close" button
                    Button btnCancel = new Button(BUTTON_CLOSE);

                    btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            dialogStage.close();
                        }
                    });

                    vbox = new VBox(5.0, new Text(FNUtil.getUserAsString(fnUser)), btnCancel);

                    // if displaying a Note
                } else if (note instanceof FNNote) {
                    FNNote fnNote = (FNNote) note;
                    // if they are updating billing
                    if(mIsBilling) {
                        //create the "Advance State" button
                        Button btnChangeState = new Button(FNBillingStateMachine.getInstance()
                                .getNextState(fnNote.getBillingState()));

                        btnChangeState.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                // advance the state of the FNNote
                                FNBillingStateMachine.getInstance().advanceState(fnNote);
                                dialogStage.close();
                                removeItem(mListView.getSelectionModel().getSelectedItem());
                            }
                        });

                        //Create the "Cancel/Close" button
                        Button btnCancel;
                        if (fnNote.getBillingState().equals(FNPConstants.BILLING_STATE_COMPLETE)) {
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
                        if (!fnNote.getBillingState().equals(FNPConstants.BILLING_STATE_COMPLETE)) {
                            vbox = new VBox(5.0, new Text(FNUtil.getNoteAsString(fnNote)), btnChangeState, btnCancel);
                        } else {
                            vbox = new VBox(5.0, new Text(FNUtil.getNoteAsString(fnNote)), btnCancel);
                        }
                    } else {
                        //Create the "Close" button
                        Button btnCancel = new Button(BUTTON_CLOSE);

                        btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                dialogStage.close();
                            }
                        });

                        vbox = new VBox(5.0, new Text(FNUtil.getNoteAsStringForReadback(fnNote)), btnCancel);
                    }
                }

                if(vbox != null) {
                    vbox.setAlignment(Pos.CENTER);
                    vbox.setPadding(new Insets(15));

                    //set the scene to the dialog
                    dialogStage.setScene(new Scene(vbox));
                    dialogStage.show();
                }
            }
        });

        // define the structure of the "preview" cells
        mListView.setCellFactory(param -> new ListCell<FieldNote>() {
            @Override
            protected void updateItem(FieldNote preview, boolean empty) {
                super.updateItem(preview, empty);

                if (empty || preview == null || preview.getId() == null) {
                    setText(null);
                } else {
                    setText(FN_NOTE_NUMBER_LABEL + " " + preview.getId() + "   ||   " +
                            FN_USERNAME_LABEL + " " + preview.getName());
                }
            }
        });
    }

    /**
     * Add an item to the ListView
     *
     * @param note to add to ListView
     */
    public void addItem(FieldNote note) {
        mList.add(note);
        Platform.runLater(() -> mListView.getItems().add(note));
    }

    /**
     * Remove an item from the ListView
     *
     * @param item
     */
    private void removeItem(FieldNote item) {
        mList.remove(item);
        Platform.runLater(() -> mListView.getItems().remove(item));
    }

    /**
     * remove ALL items from the ListView
     */
    public void removeItems() {
        mList.clear();
        Platform.runLater(() -> mListView.getItems().clear());
    }
}