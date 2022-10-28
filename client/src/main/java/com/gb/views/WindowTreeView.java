package com.gb.views;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.*;

import java.beans.EventHandler;
import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Map;

public class WindowTreeView {

    public VBox VBoxHomeWindow;
    private final TreeView <String> treeView;
    TreeItem<String> root;

    private LinkedList<File> list;


    public WindowTreeView (VBox VBoxHomeWindow){
        this.VBoxHomeWindow = VBoxHomeWindow;
        treeView = new TreeView<String>();
        this.VBoxHomeWindow.getChildren().add(treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);
        treeView.setPadding(new Insets(5.0));
        root = new TreeItem<>("Home");
        treeView.setRoot(root);
        root.setExpanded(true);

        initializeList();

//        Button btn = new Button("Test");
//        this.VBoxHomeWindow.getChildren().add(btn);
//        VBox.setVgrow(btn, Priority.ALWAYS);
//        btn.setOnAction(ActionEvent event);
    }
    private void initializeList() {

    }

    public void updateView(LinkedList<File> newList){
        this.list = newList;
        ObservableList<TreeItem<String>> userCatalog = treeView.getRoot().getChildren();
        userCatalog.remove(0, userCatalog.size());

        list.forEach((f) -> {
            TreeItem<String> item = new TreeItem<>(f.getName());
            Path path = f.toPath();
            TreeItem<String> parentCat = null;
            for (int i = 1; i < path.getNameCount() - 1; i++) {
                if (i == 1){
                    parentCat = treeView.getRoot();
                    continue;
                }
                String catName = path.getName(i).toString();
                System.out.println("�� ��� ���������: " + catName);
                TreeItem<String> cat = null;
                for (TreeItem<String> treeItem : parentCat.getChildren()) {
                    if (treeItem.toString().equals(catName)){
                        System.out.println("���� ����������");
                        cat = treeItem;
                        break;
                    }
                }
                if (cat == null){
                    TreeItem<String> newCat = new TreeItem<>(catName);
                    parentCat.getChildren().add(newCat);
                    parentCat = newCat;
                }

            }
            assert parentCat != null;
            parentCat.getChildren().add(item);
            System.out.println("��������� " + item + " � " + parentCat);
        });


    }

    public String getParentItem(ActionEvent actionEvent){
        String s = treeView.getFocusModel().getFocusedItem().getValue();

        return s;
    }

}
