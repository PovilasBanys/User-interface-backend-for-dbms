package fxControl;

//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
//import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Alert;
//import javafx.scene.control.TableCell;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.layout.HBox;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import javafx.util.Callback;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import model.Detale;
import model.Surinkejas;
import utils.DbOperations;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SurinkejasPage implements Initializable {

    @FXML private TableView<Surinkejas> surinkejasTable;
    @FXML private TableColumn<Surinkejas, String>  numerisCol;
    @FXML private TableColumn<Surinkejas, String>  vardasCol;
    @FXML private TableColumn<Surinkejas, String>  pavardeCol;
    @FXML private TableColumn<Surinkejas, String>  telefonasCol;
    @FXML private TableColumn<Surinkejas, String>  aprasymasCol;
    @FXML private TableColumn<Surinkejas, String>  kainaCol;
    @FXML private TableColumn<Surinkejas, String>  editCol;

    @FXML private ChoiceBox kainaChoiceBox;

    @FXML private TextField searchField;

private String[] kainaChoice = {"25.99", "19.5", "26.3", "32.99", "24.8", "visos kainos"};


    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Surinkejas surinkejas = null;

    ObservableList<Surinkejas> SurinkejasList = FXCollections.observableArrayList();

    public SurinkejasPage() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            con = DbOperations.connectToDb();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Failed to connect to database");
            alert.showAndWait();
        }

        kainaChoiceBox.getItems().addAll(kainaChoice);

    loadSurinkejas();

    }


    private void refreshTable() {
        try {
            SurinkejasList.clear();

            query = "SELECT * FROM SURINKEJAS ";

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                    SurinkejasList.add(new Surinkejas(
                            resultSet.getInt("SURINKEJO_NR"),
                            resultSet.getString("SURINKEJO_VARDAS"),
                            resultSet.getString("SURINKEJO_PAVARDE"),
                            resultSet.getInt("SURINKEJO_TELEFONAS"),
                            resultSet.getString("SURINKEJO_APRASYMAS"),
                            resultSet.getFloat("SURINKEJO_KAINA")
                    ));

                surinkejasTable.setItems(SurinkejasList);

        }

        } catch (SQLException ex) {
            Logger.getLogger(SurinkejasPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void search() {
        try {
            SurinkejasList.clear();

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (kainaChoiceBox.getValue() == "25.99"){
                    query = "SELECT * FROM SURINKEJAS WHERE SURINKEJO_KAINA = '25.99' ";
                    SurinkejasList.add(new Surinkejas(
                            resultSet.getInt("SURINKEJO_NR"),
                            resultSet.getString("SURINKEJO_VARDAS"),
                            resultSet.getString("SURINKEJO_PAVARDE"),
                            resultSet.getInt("SURINKEJO_TELEFONAS"),
                            resultSet.getString("SURINKEJO_APRASYMAS"),
                            resultSet.getFloat("SURINKEJO_KAINA")
                    ));

                    surinkejasTable.setItems(SurinkejasList);
                }
                else if (kainaChoiceBox.getValue() == "19.5"){
                    query = "SELECT * FROM SURINKEJAS WHERE SURINKEJO_KAINA = '19.5' ";
                    SurinkejasList.add(new Surinkejas(
                            resultSet.getInt("SURINKEJO_NR"),
                            resultSet.getString("SURINKEJO_VARDAS"),
                            resultSet.getString("SURINKEJO_PAVARDE"),
                            resultSet.getInt("SURINKEJO_TELEFONAS"),
                            resultSet.getString("SURINKEJO_APRASYMAS"),
                            resultSet.getFloat("SURINKEJO_KAINA")
                    ));

                    surinkejasTable.setItems(SurinkejasList);
                }
                if (kainaChoiceBox.getValue() == "26.3"){
                    query = "SELECT * FROM SURINKEJAS WHERE SURINKEJO_KAINA = '26.3' ";
                    SurinkejasList.add(new Surinkejas(
                            resultSet.getInt("SURINKEJO_NR"),
                            resultSet.getString("SURINKEJO_VARDAS"),
                            resultSet.getString("SURINKEJO_PAVARDE"),
                            resultSet.getInt("SURINKEJO_TELEFONAS"),
                            resultSet.getString("SURINKEJO_APRASYMAS"),
                            resultSet.getFloat("SURINKEJO_KAINA")
                    ));

                    surinkejasTable.setItems(SurinkejasList);
                }
                if (kainaChoiceBox.getValue() == "32.99"){
                    query = "SELECT * FROM SURINKEJAS WHERE SURINKEJO_KAINA = '32.99' ";
                    SurinkejasList.add(new Surinkejas(
                            resultSet.getInt("SURINKEJO_NR"),
                            resultSet.getString("SURINKEJO_VARDAS"),
                            resultSet.getString("SURINKEJO_PAVARDE"),
                            resultSet.getInt("SURINKEJO_TELEFONAS"),
                            resultSet.getString("SURINKEJO_APRASYMAS"),
                            resultSet.getFloat("SURINKEJO_KAINA")
                    ));

                    surinkejasTable.setItems(SurinkejasList);
                }
                if (kainaChoiceBox.getValue() == "24.8"){
                    query = "SELECT * FROM SURINKEJAS WHERE SURINKEJO_KAINA = '24.8' ";
                    SurinkejasList.add(new Surinkejas(
                            resultSet.getInt("SURINKEJO_NR"),
                            resultSet.getString("SURINKEJO_VARDAS"),
                            resultSet.getString("SURINKEJO_PAVARDE"),
                            resultSet.getInt("SURINKEJO_TELEFONAS"),
                            resultSet.getString("SURINKEJO_APRASYMAS"),
                            resultSet.getFloat("SURINKEJO_KAINA")
                    ));

                    surinkejasTable.setItems(SurinkejasList);
                }
                if (kainaChoiceBox.getValue() == "visos kainos"){
                    query = "SELECT * FROM SURINKEJAS ";
                    SurinkejasList.add(new Surinkejas(
                            resultSet.getInt("SURINKEJO_NR"),
                            resultSet.getString("SURINKEJO_VARDAS"),
                            resultSet.getString("SURINKEJO_PAVARDE"),
                            resultSet.getInt("SURINKEJO_TELEFONAS"),
                            resultSet.getString("SURINKEJO_APRASYMAS"),
                            resultSet.getFloat("SURINKEJO_KAINA")
                    ));

                    surinkejasTable.setItems(SurinkejasList);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(SurinkejasPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void loadSurinkejas() {

        refreshTable();

        numerisCol.setCellValueFactory(new PropertyValueFactory<>("SURINKEJO_NR"));
        vardasCol.setCellValueFactory(new PropertyValueFactory<>("SURINKEJO_VARDAS"));
        pavardeCol.setCellValueFactory(new PropertyValueFactory<>("SURINKEJO_PAVARDE"));
        telefonasCol.setCellValueFactory(new PropertyValueFactory<>("SURINKEJO_TELEFONAS"));
        aprasymasCol.setCellValueFactory(new PropertyValueFactory<>("SURINKEJO_APRASYMAS"));
        kainaCol.setCellValueFactory(new PropertyValueFactory<>("SURINKEJO_KAINA"));

        //add cell of button edit
        Callback<TableColumn<Surinkejas, String>, TableCell<Surinkejas, String>> cellFoctory = (TableColumn<Surinkejas, String> param) -> {
            // make cell containing buttons
            final TableCell<Surinkejas, String> cell = new TableCell<Surinkejas, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                        + "-glyph-size:28px;"
                                        + "-fx-fill:#00E676;"
                        );


                        deleteIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {
                            try {
                                surinkejas = surinkejasTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM SURINKEJAS WHERE SURINKEJO_NR  ="+surinkejas.getSURINKEJO_NR();
                                con = DbOperations.connectToDb();
                                preparedStatement = con.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(SurinkejasPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });


                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            surinkejas = surinkejasTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/fxml/SurinkejasInfo.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(SurinkejasPage.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            SurinkejasInfo surinkejasInfo = loader.getController();
                            surinkejasInfo.setUpdate(true);
                            surinkejasInfo.setTextField(surinkejas.getSURINKEJO_NR(), surinkejas.getSURINKEJO_VARDAS(),
                                    surinkejas.getSURINKEJO_PAVARDE(),surinkejas.getSURINKEJO_TELEFONAS(),
                                    surinkejas.getSURINKEJO_APRASYMAS(), surinkejas.getSURINKEJO_KAINA());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();




                        });


                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        surinkejasTable.setItems(SurinkejasList);
    }



    public void newBtn (ActionEvent actionEvent) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/SurinkejasInfo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

//        try {
//            Parent parent = FXMLLoader.load(getClass().getResource("/tableView/addStudent.fxml"));
//            Scene scene = new Scene(parent);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.initStyle(StageStyle.UTILITY);
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

  @FXML  private void refreshBtn (ActionEvent actionEvent) throws IOException, SQLException {

            SurinkejasList.clear();
            query = "SELECT * FROM SURINKEJAS";
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                SurinkejasList.add(new Surinkejas(
                        resultSet.getInt("SURINKEJO_NR"),
                        resultSet.getString("SURINKEJO_VARDAS"),
                        resultSet.getString("SURINKEJO_PAVARDE"),
                        resultSet.getInt("SURINKEJO_TELEFONAS"),
                        resultSet.getString("SURINKEJO_APRASYMAS"),
                        resultSet.getFloat("SURINKEJO_KAINA")
                ));

                surinkejasTable.setItems(SurinkejasList);
            }
    }


    public void backBtn (ActionEvent actionEvent) throws IOException {

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainPage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainPage.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    public void search (ActionEvent actionEvent) throws IOException {
//
//
//
//    }


    @FXML private void report() throws DocumentException, IOException, SQLException {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("Surinkejas.pdf"));
        doc.open();

        Paragraph paragraph = new Paragraph("Surinkeju ataskaita");
        doc.add(paragraph);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));

//        String query="select * from DETALE";
//        preparedStatement=con.prepareStatement(query);
//        resultSet=preparedStatement.executeQuery();
//
//        while (resultSet.next()){
//            Paragraph para=new Paragraph(resultSet.getString("DETALES_ID")
//                    +" "+resultSet.getString("PAVADINIMAS")
//                    +" "+resultSet.getString("APRASAS")
//                    +" "+resultSet.getString("KAINA")
//                    +" "+resultSet.getString("KIEKIS_SANDELYJE")
//                    +" "+resultSet.getString("TIPAS"));
//            doc.add(para);
//            doc.add(new Paragraph(" "));
//        }

//        String query1="select SUM(KIEKIS_SANDELYJE) as kiekis from DETALE";
//        preparedStatement=con.prepareStatement(query1);
//        resultSet=preparedStatement.executeQuery();
//
//        while (resultSet.next()) {
//            Paragraph para1 = new Paragraph(resultSet.getString("kiekis"));
//            doc.add(para1);
//            doc.add(new Paragraph(" "));
//        }
//
//        String query2=" select DETALE.DETALES_ID as dd, DETALE.PAVADINIMAS as dp, PASIRINKTOS_DETALES.KIEKIS as pdk,\n" +
//                "                UZSAKYMAS.UZSAKYMO_NR as uu\n" +
//                "        from DETALE\n" +
//                "        INNER JOIN PASIRINKTOS_DETALES on DETALE.DETALES_ID=PASIRINKTOS_DETALES.DETALES_ID\n" +
//                "        INNER JOIN UZSAKYMAS on PASIRINKTOS_DETALES.UZSAKYMO_NR=UZSAKYMAS.UZSAKYMO_NR;";
//        preparedStatement=con.prepareStatement(query2);
//        resultSet=preparedStatement.executeQuery();
//
//        while (resultSet.next()){
//            Paragraph para2=new Paragraph(resultSet.getString("dd")
//                    +" "+resultSet.getString("dp")
//                    +" "+resultSet.getString("pdk")
//                    +" "+resultSet.getString("uu"));
//            doc.add(para2);
//            doc.add(new Paragraph(" "));
//        }





        PdfPTable table1 = new PdfPTable(6);

        PdfPCell c1 = new PdfPCell(new Phrase("Surinkejo nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Vardas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Pavarde"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Telefonas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Apradymas"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Kaina"));
        table1.addCell(c1);

        table1.setHeaderRows(1);

        String query1="select * from SURINKEJAS";
        preparedStatement=con.prepareStatement(query1);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table1.addCell(resultSet.getString("SURINKEJO_NR"));
            table1.addCell(resultSet.getString("SURINKEJO_VARDAS"));
            table1.addCell(resultSet.getString("SURINKEJO_PAVARDE"));
            table1.addCell(resultSet.getString("SURINKEJO_TELEFONAS"));
            table1.addCell(resultSet.getString("SURINKEJO_APRASYMAS"));
            table1.addCell(resultSet.getString("SURINKEJO_KAINA"));
        }

        Paragraph paragraph1 = new Paragraph("Surinkeju sarasas");
        doc.add(paragraph1);
        doc.add(new Paragraph(" "));

        doc.add(table1);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        PdfPTable table2 = new PdfPTable(8);

        PdfPCell c2 = new PdfPCell(new Phrase("Surinkejo nr."));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Surinkejo vardas"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Surinkejo pavarde"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales ID"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales pavadinimas"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Kiekis"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Pabaigos data"));
        table2.addCell(c2);

        table2.setHeaderRows(1);


        String query2="select SURINKEJAS.SURINKEJO_NR as ssn, SURINKEJAS.SURINKEJO_VARDAS as ssv, SURINKEJAS.SURINKEJO_PAVARDE as ssp, UZSAKYMAS.UZSAKYMO_NR as uun,\n" +
                "DETALE.DETALES_ID as ddi, DETALE.PAVADINIMAS as dp, PASIRINKTOS_DETALES.KIEKIS as pdk, UZSAKYMAS.DATA_IKI as ud\n" +
                "from DETALE\n" +
                "inner join PASIRINKTOS_DETALES on DETALE.DETALES_ID = PASIRINKTOS_DETALES.DETALES_ID\n" +
                "inner join UZSAKYMAS on PASIRINKTOS_DETALES.UZSAKYMO_NR = UZSAKYMAS.UZSAKYMO_NR\n" +
                "inner join SURINKEJAS on UZSAKYMAS.SURINKEJO_NR = SURINKEJAS.SURINKEJO_NR\n" +
                "order by SURINKEJAS.SURINKEJO_NR";
        preparedStatement=con.prepareStatement(query2);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table2.addCell(resultSet.getString("ssn"));
            table2.addCell(resultSet.getString("ssv"));
            table2.addCell(resultSet.getString("ssp"));
            table2.addCell(resultSet.getString("uun"));
            table2.addCell(resultSet.getString("ddi"));
            table2.addCell(resultSet.getString("dp"));
            table2.addCell(resultSet.getString("pdk"));
            table2.addCell(resultSet.getString("ud"));

        }

        Paragraph paragraph2 = new Paragraph("Surinkejui priklausantis pirkejas, uzsakymas, detales ir baigimo datos");
        doc.add(paragraph2);
        doc.add(new Paragraph(" "));

        doc.add(table2);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));



        String query5="select count(PASIRINKTOS_DETALES.PASIRINKTOS_DETALES_ID) as ddi\n" +
                "from UZSAKYMAS\n" +
                "inner join SURINKEJAS on UZSAKYMAS.SURINKEJO_NR = SURINKEJAS.SURINKEJO_NR\n" +
                "inner join PASIRINKTOS_DETALES on UZSAKYMAS.UZSAKYMO_NR = PASIRINKTOS_DETALES.UZSAKYMO_NR\n" +
                "inner join DETALE on PASIRINKTOS_DETALES.DETALES_ID = DETALE.DETALES_ID";
        preparedStatement=con.prepareStatement(query5);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){
            Paragraph para=new Paragraph("Reikalingu surinkti detaliu skaicius:" + " " + resultSet.getString("ddi"));
            doc.add(para);
            doc.add(new Paragraph(" "));
        }

        String query6="select count(SURINKEJAS.SURINKEJO_NR) as ssn\n" +
                "from UZSAKYMAS\n" +
                "inner join SURINKEJAS on UZSAKYMAS.SURINKEJO_NR = SURINKEJAS.SURINKEJO_NR";
        preparedStatement=con.prepareStatement(query6);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){
            Paragraph para=new Paragraph("Visu surinkimu skaicius:" + " " + resultSet.getString("ssn"));
            doc.add(para);
            doc.add(new Paragraph(" "));
        }


        doc.close();

        File myFile = new File("C:\\Users\\Dalius\\Desktop\\final mssql nuo nulio old jdk\\CourseraGUI_JDBC\\Surinkejas.pdf");
        Desktop.getDesktop().open(myFile);

    }

}
