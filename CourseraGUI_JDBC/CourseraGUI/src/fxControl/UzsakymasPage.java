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
import model.Uzsakymas;
import utils.DbOperations;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UzsakymasPage implements Initializable {

    @FXML
    private TableView<Uzsakymas> uzsakymasTable;
    @FXML
    private TableColumn<Uzsakymas, String> uzsakymasCol;
    @FXML
    private TableColumn<Uzsakymas, String> kurjerisCol;
    @FXML
    private TableColumn<Uzsakymas, String> surinkejasCol;
    @FXML
    private TableColumn<Uzsakymas, String> pirkejasCol;
    @FXML
    private TableColumn<Uzsakymas, String> dataCol;
    @FXML
    private TableColumn<Uzsakymas, String> editCol;

    @FXML
    private ListView<String> allCustomersListView;
//    @FXML private ComboBox uzsakymai;


//    @FXML private ChoiceBox typeChoiceBox;

    @FXML
    private TextField searchField;

//private String[] typeChoice = {"privaloma", "neprivaloma", "visi tipai"};


    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Uzsakymas uzsakymas = null;

    ObservableList<Uzsakymas> UzsakymasList = FXCollections.observableArrayList();




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

        //     typeChoiceBox.getItems().addAll(typeChoice);

        loadUzsakymas();

//        try {
//            String query = "SELECT * FROM UZSAKYMAS";
//            Statement st = con.createStatement();
//            ResultSet result = st.executeQuery(query);
//            ObservableList data = FXCollections.observableArrayList();
//
//            while (result.next()) {
//
//                data.add(new String(result.getString("UZSAKYMO_NR")));
//            }
//            uzsakymai.setItems(data);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    private void refreshTable() {
        try {
            UzsakymasList.clear();

            query = "SELECT * FROM UZSAKYMAS ";

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                UzsakymasList.add(new Uzsakymas(
                        resultSet.getInt("UZSAKYMO_NR"),
                        resultSet.getInt("KURJERIO_NR"),
                        resultSet.getInt("SURINKEJO_NR"),
                        resultSet.getInt("REGISTRACIJOS_NR"),
                        resultSet.getDate("DATA_IKI")
                ));

                uzsakymasTable.setItems(UzsakymasList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UzsakymasPage.class.getName()).log(Level.SEVERE, null, ex);
        }


//        try {
//        String query = "SELECT * FROM UZSAKYMAS";
//            Statement st = con.createStatement();
//            ResultSet result = st.executeQuery(query);
//            ObservableList data = FXCollections.observableArrayList();
//
//            while (result.next()) {
//
//                data.add(new String(result.getString("UZSAKYMO_NR")));
//            }
//            uzsakymai.setItems(data);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }



//    @FXML
//    private void search() {
//        try {
//            DetailList.clear();
//
//            preparedStatement = con.prepareStatement(query);
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                if (typeChoiceBox.getValue() == "privaloma"){
//                    query = "SELECT * FROM DETALE WHERE TIPAS = 'privaloma' ";
//                    DetailList.add(new Detale(
//                            resultSet.getInt("DETALES_ID"),
//                            resultSet.getString("PAVADINIMAS"),
//                            resultSet.getString("APRASAS"),
//                            resultSet.getFloat("KAINA"),
//                            resultSet.getInt("KIEKIS_SANDELYJE"),
//                            resultSet.getString("TIPAS")
//                    ));
//
//                    detailsTable.setItems(DetailList);
//                }
//               else if (typeChoiceBox.getValue() == "neprivaloma"){
//                    query = "SELECT * FROM DETALE WHERE TIPAS = 'neprivaloma' ";
//                    DetailList.add(new Detale(
//                            resultSet.getInt("DETALES_ID"),
//                            resultSet.getString("PAVADINIMAS"),
//                            resultSet.getString("APRASAS"),
//                            resultSet.getFloat("KAINA"),
//                            resultSet.getInt("KIEKIS_SANDELYJE"),
//                            resultSet.getString("TIPAS")
//                    ));
//
//                    detailsTable.setItems(DetailList);
//                }
//                else if (typeChoiceBox.getValue() == "visi tipai"){
//                    query = "SELECT * FROM DETALE ";
//                    DetailList.add(new Detale(
//                            resultSet.getInt("DETALES_ID"),
//                            resultSet.getString("PAVADINIMAS"),
//                            resultSet.getString("APRASAS"),
//                            resultSet.getFloat("KAINA"),
//                            resultSet.getInt("KIEKIS_SANDELYJE"),
//                            resultSet.getString("TIPAS")
//                    ));
//
//                    detailsTable.setItems(DetailList);
//                }
//
//            }
//
//        } catch (SQLException ex) {
//            Logger.getLogger(UzsakymasPage.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//


    private void loadUzsakymas() {

        refreshTable();

        uzsakymasCol.setCellValueFactory(new PropertyValueFactory<>("UZSAKYMO_NR"));
        kurjerisCol.setCellValueFactory(new PropertyValueFactory<>("KURJERIO_NR"));
        surinkejasCol.setCellValueFactory(new PropertyValueFactory<>("SURINKEJO_NR"));
        pirkejasCol.setCellValueFactory(new PropertyValueFactory<>("REGISTRACIJOS_NR"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("DATA_IKI"));

        //add cell of button edit
        Callback<TableColumn<Uzsakymas, String>, TableCell<Uzsakymas, String>> cellFoctory = (TableColumn<Uzsakymas, String> param) -> {
            // make cell containing buttons
            final TableCell<Uzsakymas, String> cell = new TableCell<Uzsakymas, String>() {
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
                                uzsakymas = uzsakymasTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM UZSAKYMAS WHERE UZSAKYMO_NR  =" + uzsakymas.getUZSAKYMO_NR();
                                con = DbOperations.connectToDb();
                                preparedStatement = con.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(UzsakymasPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            uzsakymas = uzsakymasTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/fxml/UzsakymasInfo.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(UzsakymasPage.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            UzsakymasInfo uzsakymasInfo = loader.getController();
                            uzsakymasInfo.setUpdate(true);
                            uzsakymasInfo.setTextField(uzsakymas.getUZSAKYMO_NR(), uzsakymas.getKURJERIO_NR(),
                                    uzsakymas.getSURINKEJO_NR(), uzsakymas.getREGISTRACIJOS_NR(), uzsakymas.getDATA_IKI());
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
        uzsakymasTable.setItems(UzsakymasList);
    }


    public void newBtn(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/UzsakymasInfo.fxml"));
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

    @FXML
    private void refreshBtn(ActionEvent actionEvent) throws IOException, SQLException {

        UzsakymasList.clear();
        query = "SELECT * FROM UZSAKYMAS";
        preparedStatement = con.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            UzsakymasList.add(new Uzsakymas(
                    resultSet.getInt("UZSAKYMO_NR"),
                    resultSet.getInt("KURJERIO_NR"),
                    resultSet.getInt("SURINKEJO_NR"),
                    resultSet.getInt("REGISTRACIJOS_NR"),
                    resultSet.getDate("DATA_IKI")
            ));

            uzsakymasTable.setItems(UzsakymasList);
        }
    }


    public void backBtn(ActionEvent actionEvent) throws IOException {

//            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainPage.fxml"));
//            Parent root = loader.load();
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainPage.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    public void search (ActionEvent actionEvent) throws IOException {
//
//
//
//    }

    @FXML
    private void report() throws DocumentException, IOException, SQLException {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("Uzsakymas.pdf"));
        doc.open();

        Paragraph paragraph = new Paragraph("Uzsakymu ataskaita");
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


        PdfPTable table1 = new PdfPTable(5);

        PdfPCell c1 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Kurjerio nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Surinkejo nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Pirkejo nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Baigimo data"));
        table1.addCell(c1);

        table1.setHeaderRows(1);

        String query1 = "select * from UZSAKYMAS";
        preparedStatement = con.prepareStatement(query1);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            table1.addCell(resultSet.getString("UZSAKYMO_NR"));
            table1.addCell(resultSet.getString("KURJERIO_NR"));
            table1.addCell(resultSet.getString("SURINKEJO_NR"));
            table1.addCell(resultSet.getString("REGISTRACIJOS_NR"));
            table1.addCell(resultSet.getString("DATA_IKI"));
        }

        Paragraph paragraph1 = new Paragraph("Uzsakymu sarasas");
        doc.add(paragraph1);
        doc.add(new Paragraph(" "));

        doc.add(table1);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));


        PdfPTable table2 = new PdfPTable(4);

        PdfPCell c2 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales ID"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Detales pavadinimas"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Kiekis"));
        table2.addCell(c2);

        table2.setHeaderRows(1);


        String query2 = "select UZSAKYMAS.UZSAKYMO_NR as uun, DETALE.DETALES_ID as ddi, DETALE.PAVADINIMAS as dp,\n" +
                "PASIRINKTOS_DETALES.KIEKIS as pkd\n" +
                "from UZSAKYMAS\n" +
                "inner join PASIRINKTOS_DETALES on UZSAKYMAS.UZSAKYMO_NR = PASIRINKTOS_DETALES.UZSAKYMO_NR\n" +
                "inner join DETALE on PASIRINKTOS_DETALES.DETALES_ID = DETALE.DETALES_ID\n" +
                "order by uun;";
        preparedStatement = con.prepareStatement(query2);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            table2.addCell(resultSet.getString("uun"));
            table2.addCell(resultSet.getString("ddi"));
            table2.addCell(resultSet.getString("dp"));
            table2.addCell(resultSet.getString("pkd"));

        }

        Paragraph paragraph2 = new Paragraph("Uzsakymo detales ir ju kiekis");
        doc.add(paragraph2);
        doc.add(new Paragraph(" "));

        doc.add(table2);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));


        PdfPTable table3 = new PdfPTable(2);

        PdfPCell c3 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("Detales kaina"));
        table3.addCell(c3);

        table3.setHeaderRows(1);


        String query3 = "select UZSAKYMAS.UZSAKYMO_NR as uun, sum(DETALE.KAINA) as dk\n" +
                "from UZSAKYMAS\n" +
                "inner join PASIRINKTOS_DETALES on UZSAKYMAS.UZSAKYMO_NR = PASIRINKTOS_DETALES.UZSAKYMO_NR\n" +
                "inner join DETALE on PASIRINKTOS_DETALES.DETALES_ID = DETALE.DETALES_ID\n" +
                "Group by rollup (UZSAKYMAS.UZSAKYMO_NR);";
        preparedStatement = con.prepareStatement(query3);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            table3.addCell(resultSet.getString("uun"));
            table3.addCell(resultSet.getString("dk"));

        }

        Paragraph paragraph3 = new Paragraph("Uzsakymo detaliu kaina ir bendra detaliu kaina");
        doc.add(paragraph3);
        doc.add(new Paragraph(" "));

        doc.add(table3);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));


        PdfPTable table4 = new PdfPTable(2);

        PdfPCell c4 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table4.addCell(c4);

        c4 = new PdfPCell(new Phrase("Kurjerio kaina"));
        table4.addCell(c4);

        table4.addCell(c4);

        table4.setHeaderRows(1);


        String query4 = "select UZSAKYMAS.UZSAKYMO_NR as uun, sum(KURJERIS.KURJERIO_KAINA) as kkn\n" +
                "from UZSAKYMAS\n" +
                "inner join PASIRINKTOS_DETALES on UZSAKYMAS.UZSAKYMO_NR = PASIRINKTOS_DETALES.UZSAKYMO_NR\n" +
                "inner join KURJERIS on UZSAKYMAS.KURJERIO_NR = KURJERIS.KURJERIO_NR\n" +
                "Group by rollup (UZSAKYMAS.UZSAKYMO_NR);";
        preparedStatement = con.prepareStatement(query4);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            table4.addCell(resultSet.getString("uun"));
            table4.addCell(resultSet.getString("kkn"));


        }

        Paragraph paragraph4 = new Paragraph("Uzsakymo kurjerio kaina ir bendra kurjeriu kaina");
        doc.add(paragraph4);
        doc.add(new Paragraph(" "));

        doc.add(table4);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));


        PdfPTable table5 = new PdfPTable(2);

        PdfPCell c5 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table5.addCell(c5);

        c5 = new PdfPCell(new Phrase("Surinkejo kaina"));
        table5.addCell(c5);

        table5.addCell(c5);

        table5.setHeaderRows(1);


        String query5 = "select UZSAKYMAS.UZSAKYMO_NR as uun,\n" +
                "sum(SURINKEJAS.SURINKEJO_KAINA) as ssk\n" +
                "from UZSAKYMAS\n" +
                "inner join PASIRINKTOS_DETALES on UZSAKYMAS.UZSAKYMO_NR = PASIRINKTOS_DETALES.UZSAKYMO_NR\n" +
                "inner join SURINKEJAS on UZSAKYMAS.SURINKEJO_NR = SURINKEJAS.SURINKEJO_NR\n" +
                "Group by rollup (UZSAKYMAS.UZSAKYMO_NR);\n";
        preparedStatement = con.prepareStatement(query5);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            table5.addCell(resultSet.getString("uun"));
            table5.addCell(resultSet.getString("ssk"));


        }

        Paragraph paragraph5 = new Paragraph("Uzsakymo surinkejo kaina ir bendra surinkeju kaina");
        doc.add(paragraph5);
        doc.add(new Paragraph(" "));

        doc.add(table5);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));


        doc.close();

        File myFile = new File("C:\\Users\\Dalius\\Desktop\\final mssql nuo nulio old jdk\\CourseraGUI_JDBC\\Uzsakymas.pdf");
        Desktop.getDesktop().open(myFile);

    }

//
//    public void load(ActionEvent actionEvent) {
////        String query = "SELECT * FROM [db teisinga su data].INFORMATION_SCHEMA.TABLES";
////        try {
////            Statement st = con.createStatement();
////            ResultSet result = st.executeQuery(query);
////            ObservableList data = FXCollections.observableArrayList();
////
////            while (result.next()) {
////
////                data.add(new String(result.getString(3)));
////            }
////            uzsakymai.setItems(data);
////
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
//
//    }



}
