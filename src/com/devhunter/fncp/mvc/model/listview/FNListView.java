/**
 * � 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.model.listview;

import com.devhunter.fncp.mvc.model.FieldNote;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

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
                //TODO: open JFX Pane
                System.out.println("Clicked an FNDataPreview " + event.getButton().toString());
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
                    //TODO: update the preview to what Ken wants
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
