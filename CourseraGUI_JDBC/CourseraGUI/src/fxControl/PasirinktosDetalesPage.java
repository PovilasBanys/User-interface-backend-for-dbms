package fxControl;

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
import model.PasirinktaDetale;
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

public class PasirinktosDetalesPage implements Initializable {

    @FXML private TableView<PasirinktaDetale> pasirintosDetalesTable;
    @FXML private TableColumn<PasirinktaDetale, String>  pasirintosDetalesIdCol;
    @FXML private TableColumn<PasirinktaDetale, String>  kiekisCol;
    @FXML private TableColumn<PasirinktaDetale, String>  detalesIdCol;
    @FXML private TableColumn<PasirinktaDetale, String>  uzsakymoNumerisCol;
    @FXML private TableColumn<PasirinktaDetale, String>  surinkimasCol;
    @FXML private TableColumn<PasirinktaDetale, String>  editCol;


    @FXML private ChoiceBox detalesIdChoiceBox;

    @FXML private TextField searchField;

private String[] detalesIdChoice = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "visos detales"};


    private Connection con;
    String query = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    PasirinktaDetale pasirinktaDetale = null;

    ObservableList<PasirinktaDetale> PasirinktaDetaleList = FXCollections.observableArrayList();


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

        detalesIdChoiceBox.getItems().addAll(detalesIdChoice);

    loadPasirinktaDetale();

    }


    private void refreshTable() {
        try {
            PasirinktaDetaleList.clear();

            query = "SELECT * FROM PASIRINKTOS_DETALES ";

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PasirinktaDetaleList.add(new PasirinktaDetale(
                        resultSet.getInt("KIEKIS"),
                        resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                        resultSet.getInt("DETALES_ID"),
                        resultSet.getInt("UZSAKYMO_NR"),
                        resultSet.getString("SURINKIMAS")
                ));

                pasirintosDetalesTable.setItems(PasirinktaDetaleList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(PasirinktosDetalesPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadPasirinktaDetale() {

        refreshTable();

        kiekisCol.setCellValueFactory(new PropertyValueFactory<>("KIEKIS"));
        pasirintosDetalesIdCol.setCellValueFactory(new PropertyValueFactory<>("PASIRINKTOS_DETALES_ID"));
        detalesIdCol.setCellValueFactory(new PropertyValueFactory<>("DETALES_ID"));
        uzsakymoNumerisCol.setCellValueFactory(new PropertyValueFactory<>("UZSAKYMO_NR"));
        surinkimasCol.setCellValueFactory(new PropertyValueFactory<>("SURINKIMAS"));

        //add cell of button edit
        Callback<TableColumn<PasirinktaDetale, String>, TableCell<PasirinktaDetale, String>> cellFoctory = (TableColumn<PasirinktaDetale, String> param) -> {
            // make cell containing buttons
            final TableCell<PasirinktaDetale, String> cell = new TableCell<PasirinktaDetale, String>() {
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
                                pasirinktaDetale = pasirintosDetalesTable.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM PASIRINKTOS_DETALES WHERE PASIRINKTOS_DETALES_ID  ="+pasirinktaDetale.getPASIRINKTOS_DETALES_ID();
                                con = DbOperations.connectToDb();
                                preparedStatement = con.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(PasirinktosDetalesPage.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });


                        editIcon.setOnMouseClicked((javafx.scene.input.MouseEvent event) -> {

                            pasirinktaDetale = pasirintosDetalesTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/fxml/PasirinktosDetalesInfo.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(PasirinktosDetalesPage.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            PasirinktosDetalesInfo pasirinktosDetalesInfo = loader.getController();
                            pasirinktosDetalesInfo.setUpdate(true);
                            pasirinktosDetalesInfo.setTextField(pasirinktaDetale.getKIEKIS(), pasirinktaDetale.getPASIRINKTOS_DETALES_ID(),
                                    pasirinktaDetale.getDETALES_ID(),pasirinktaDetale.getUZSAKYMO_NR(), pasirinktaDetale.getSURINKIMAS());
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
        pasirintosDetalesTable.setItems(PasirinktaDetaleList);
    }


    public void backBtn(ActionEvent actionEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainPage.fxml"));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();

        Parent root = FXMLLoader.load(getClass().getResource("../fxml/MainPage.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void newBtn(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/PasirinktosDetalesInfo.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void refreshBtn(ActionEvent actionEvent) throws SQLException {

        PasirinktaDetaleList.clear();
        query = "SELECT * FROM PASIRINKTOS_DETALES";
        preparedStatement = con.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            PasirinktaDetaleList.add(new PasirinktaDetale(
                    resultSet.getInt("KIEKIS"),
                    resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                    resultSet.getInt("DETALES_ID"),
                    resultSet.getInt("UZSAKYMO_NR"),
                    resultSet.getString("SURINKIMAS")
            ));

            pasirintosDetalesTable.setItems(PasirinktaDetaleList);
        }
    }

    public void search(ActionEvent actionEvent) {

        try {
            PasirinktaDetaleList.clear();

            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (detalesIdChoiceBox.getValue() == "1"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '1' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }
                else if (detalesIdChoiceBox.getValue() == "2"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '2' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if(detalesIdChoiceBox.getValue() == "3"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '3' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if(detalesIdChoiceBox.getValue() == "4"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '4' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if (detalesIdChoiceBox.getValue() == "5"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '5' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if (detalesIdChoiceBox.getValue() == "6"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '6' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if (detalesIdChoiceBox.getValue() == "7"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '7' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }
                else if (detalesIdChoiceBox.getValue() == "8"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '8' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if (detalesIdChoiceBox.getValue() == "9"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '9' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if (detalesIdChoiceBox.getValue() == "10"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '10' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if(detalesIdChoiceBox.getValue() == "11"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '11' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if(detalesIdChoiceBox.getValue() == "12"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '12' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if (detalesIdChoiceBox.getValue() == "13"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '13' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if(detalesIdChoiceBox.getValue() == "14"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '14' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if(detalesIdChoiceBox.getValue() == "15"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '15' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if(detalesIdChoiceBox.getValue() == "16"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '16' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if(detalesIdChoiceBox.getValue() == "17"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '17' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }
                else if(detalesIdChoiceBox.getValue() == "18"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES WHERE DETALES_ID = '18' ";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

                else if (detalesIdChoiceBox.getValue() == "visos detales"){
                    query = "SELECT * FROM PASIRINKTOS_DETALES";
                    PasirinktaDetaleList.add(new PasirinktaDetale(
                            resultSet.getInt("KIEKIS"),
                            resultSet.getInt("PASIRINKTOS_DETALES_ID"),
                            resultSet.getInt("DETALES_ID"),
                            resultSet.getInt("UZSAKYMO_NR"),
                            resultSet.getString("SURINKIMAS")
                    ));

                    pasirintosDetalesTable.setItems(PasirinktaDetaleList);
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(PasirinktosDetalesPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML private void report() throws DocumentException, IOException, SQLException {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("PasirinktosDetales.pdf"));
        doc.open();

        Paragraph paragraph = new Paragraph("Pasirinktu detaliu ataskaita");
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

        PdfPCell c1 = new PdfPCell(new Phrase("Pasirinktos detales id"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Detales id"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Kiekis"));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table1.addCell(c1);

        c1 = new PdfPCell(new Phrase("Surinkimas"));
        table1.addCell(c1);

        table1.setHeaderRows(1);

        String query1="select * from PASIRINKTOS_DETALES";
        preparedStatement=con.prepareStatement(query1);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table1.addCell(resultSet.getString("PASIRINKTOS_DETALES_ID"));
            table1.addCell(resultSet.getString("DETALES_ID"));
            table1.addCell(resultSet.getString("KIEKIS"));
            table1.addCell(resultSet.getString("UZSAKYMO_NR"));
            table1.addCell(resultSet.getString("SURINKIMAS"));
        }

        Paragraph paragraph1 = new Paragraph("Pasirinktu detaliu sarasas");
        doc.add(paragraph1);
        doc.add(new Paragraph(" "));

        doc.add(table1);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        PdfPTable table2 = new PdfPTable(5);

        PdfPCell c2 = new PdfPCell(new Phrase("Pasirinktos detales id"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Pirkejo nr."));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Pirkejo pavarde"));
        table2.addCell(c2);

        c2 = new PdfPCell(new Phrase("Pirkejo vardas"));
        table2.addCell(c2);

        table2.setHeaderRows(1);


        String query2="select PASIRINKTOS_DETALES.PASIRINKTOS_DETALES_ID as pdd, UZSAKYMAS.UZSAKYMO_NR as uun, \n" +
                "PIRKEJAS.REGISTRACIJOS_NR as prn, PIRKEJAS.PAVARDE as pp ,PIRKEJAS.VARDAS as pv\n" +
                "from PASIRINKTOS_DETALES\n" +
                "inner join UZSAKYMAS on PASIRINKTOS_DETALES.UZSAKYMO_NR = UZSAKYMAS.UZSAKYMO_NR\n" +
                "inner join PIRKEJAS on UZSAKYMAS.REGISTRACIJOS_NR = PIRKEJAS.REGISTRACIJOS_NR\n" +
                "order by pdd;";
        preparedStatement=con.prepareStatement(query2);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table2.addCell(resultSet.getString("pdd"));
            table2.addCell(resultSet.getString("uun"));
            table2.addCell(resultSet.getString("prn"));
            table2.addCell(resultSet.getString("pp"));
            table2.addCell(resultSet.getString("pv"));

        }

        Paragraph paragraph2 = new Paragraph("Pasirinktos detales prklausymas uzsakymui ir pirkejui");
        doc.add(paragraph2);
        doc.add(new Paragraph(" "));

        doc.add(table2);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        PdfPTable table3 = new PdfPTable(2);

        PdfPCell c3 = new PdfPCell(new Phrase("Uzsakymo nr."));
        table3.addCell(c3);

        c3 = new PdfPCell(new Phrase("Pasirinktos detales kaina"));
        table3.addCell(c3);

        table3.setHeaderRows(1);


        String query3="select UZSAKYMAS.UZSAKYMO_NR as uun, sum(DETALE.KAINA) as dk\n" +
                "from UZSAKYMAS\n" +
                "inner join PASIRINKTOS_DETALES on UZSAKYMAS.UZSAKYMO_NR = PASIRINKTOS_DETALES.UZSAKYMO_NR\n" +
                "inner join DETALE on PASIRINKTOS_DETALES.DETALES_ID = DETALE.DETALES_ID\n" +
                "\n" +
                "Group by rollup (UZSAKYMAS.UZSAKYMO_NR);";
        preparedStatement=con.prepareStatement(query3);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){

            table3.addCell(resultSet.getString("uun"));
            table3.addCell(resultSet.getString("dk"));

        }

        Paragraph paragraph3 = new Paragraph("Uzsakymo pasirinktu detaliu kaina ir bendra detaliu suma");
        doc.add(paragraph3);
        doc.add(new Paragraph(" "));

        doc.add(table3);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));




        String query5="select count(PASIRINKTOS_DETALES.PASIRINKTOS_DETALES_ID) as pdd\n" +
                "from PASIRINKTOS_DETALES";
        preparedStatement=con.prepareStatement(query5);
        resultSet=preparedStatement.executeQuery();

        while (resultSet.next()){
            Paragraph para=new Paragraph("Is viso pasirinkta detaliu:" + " " + resultSet.getString("pdd"));
            doc.add(para);
            doc.add(new Paragraph(" "));
        }


        doc.close();

        File myFile = new File("C:\\Users\\Dalius\\Desktop\\final mssql nuo nulio old jdk\\CourseraGUI_JDBC\\PasirinktosDetales.pdf");
        Desktop.getDesktop().open(myFile);

    }



}
