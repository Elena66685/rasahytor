package com.example.rosakhutor;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.*;

import javax.imageio.ImageIO;
import javax.management.relation.Role;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.example.rosakhutor.GlobalVars.*;
import static java.nio.file.Files.readAllBytes;


public class OrderController {

    SellerController sellerController = new SellerController();
    DbConnector dbConnector = new DbConnector();
    int last_id;
    String code;
    private final Stage primaryStage = new Stage();
    int id_client;

    @FXML
    public Button back;

    @FXML
    private Button barcode;

    @FXML
    private TextField orderNumber;

    @FXML
    private Slider slider;

    @FXML
    private TextField slidertext;

    @FXML
    private Button all_clients;

    @FXML
    private Button ad_a_client;

    public void initialize() throws SQLException, ClassNotFoundException { // Метод автоматически вызывается после загрузки FXML
        System.out.println("Контроллер загружен");
        OrderNumber();
        SliderValuo();

    }

    public void SliderValuo(){
        slidertext.textProperty().bind(slider.valueProperty().asString("%.0f" + "мин."));
        slidertext.setEditable(false);
    }


    public void OpenSellerWindow() throws IOException {
        sellerController.OpenEmployeeWindow(Role, Images, Name, (Stage) back.getScene().getWindow());
    }

    public void OrderNumber() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbConnector.getOrderCode();
        System.out.println(resultSet);
        if(resultSet.next()) {
            int code_int = Integer.parseInt(resultSet.getString("cod")) + 1;
            code = String.valueOf(code_int);
            System.out.println(code);
            orderNumber.setText(code);
            orderNumber.setEditable(false);
        }
    }

    public void Barcode() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbConnector.getOrderCode();
        System.out.println(resultSet);
        if (resultSet.next()) {
            int code_int = Integer.parseInt(resultSet.getString("cod")) + 1;
            code = String.valueOf(code_int);
            System.out.println(code);

        }
        // Получаем текущий момент времени
        LocalDateTime now = LocalDateTime.now();
        Timestamp ts = Timestamp.valueOf(now);
        System.out.println(ts.toString());
        String result = ts.toString().substring(0, 19);
        System.out.println(result);
        String dataTime = result.replaceAll("[^\\d]", "");
        System.out.println(dataTime);
        Random random = new Random();
        int minValue = 100000;
        int maxValue = 999999;
        int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue;
        String randomStr = String.valueOf(randomNumber);
        System.out.println(randomStr);
        String barcode = code + dataTime + randomStr;
        System.out.println(barcode);
        // Извлекаем только дату
        //LocalDate dateOnly = now.toLocalDate();

        // Формируем форматированный вывод
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        //String formattedDate = dateOnly.format(formatter);

        //System.out.println("Только сегодняшняя дата: " + formattedDate);

        //Time time = new Time(System.currentTimeMillis());

        //dbConnector.singUpOrders(code, ts, 45462562, 2, "04.04.2022", 600);

        try {
            // Шаг 1: Получаем директорию для сохранения
            File selectedDirectory = selectSaveDirectory(primaryStage);
            if (selectedDirectory == null) {
                showError("Директория не выбрана");
                //return null;
            }

            // Шаг 2: Проверяем права записи
            if (!checkWritePermissions(selectedDirectory)) {
                showError("Нет прав записи в выбранную директорию: " + selectedDirectory.getAbsolutePath());
                //return null;
            }

            // Шаг 3: Подготавливаем данные для штрих-кода (убираем пробелы)
            String barcodeData = barcode.replace(" ", "");
            System.out.println("Создаем штрих-код для данных: " + barcode);

            // Шаг 4: Создаем штрих-код
            Code128Writer barcodeWriter = new Code128Writer();
            BitMatrix bitMatrix = barcodeWriter.encode(barcode, BarcodeFormat.CODE_128, 400, 150);

            // Шаг 5: Создаем изображение с текстом
            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);
            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);
            BufferedImage finalImage = addTextToBarcode(barcodeImage, barcode);

            // Шаг 6: Генерируем уникальное имя файла
            String fileName = "barcode_image.png";
            Path filePath = Paths.get(selectedDirectory.getAbsolutePath(), fileName);

            // Шаг 7: Сохраняем файл с использованием ImageIO (более надежно)
            ImageIO.write(finalImage, "PNG", filePath.toFile());

            String successMessage = "Штрих-код успешно сохранен!\n" +
                    "Файл: " + filePath.toString() + "\n" +
                    "Данные: " + barcode + "\n" +
                    "Штрих-код: " + barcodeData;

            showSuccess(successMessage);
            //return filePath.toString();

        } catch (IOException e) {
            showError("Ошибка при сохранении файла: " + e.getMessage());
            //return null;
        } catch (Exception e) {
            showError("Неожиданная ошибка: " + e.getMessage());
            //return null;
        }
    }

    private static File selectSaveDirectory(Stage primaryStage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выберите папку для сохранения штрих-кода");

        // Начинаем с безопасных директорий
        File initialDir = new File(System.getProperty("user.home"));
        directoryChooser.setInitialDirectory(initialDir);

        return directoryChooser.showDialog(primaryStage);
    }

    private static boolean checkWritePermissions(File directory) {
        if (!directory.exists() || !directory.isDirectory()) {
            return false;
        }

        // Тестируем запись созданием временного файла
        File testFile = new File(directory, "write_test_" + System.currentTimeMillis() + ".tmp");
        try {
            if (testFile.createNewFile()) {
                testFile.delete();
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private static BufferedImage addTextToBarcode(BufferedImage barcodeImage, String text) {
        int textHeight = 40;
        int totalHeight = barcodeImage.getHeight() + textHeight;

        BufferedImage combinedImage = new BufferedImage(
                barcodeImage.getWidth(),
                totalHeight,
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2d = combinedImage.createGraphics();

        // Настраиваем качество рендеринга
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Белый фон
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, combinedImage.getWidth(), combinedImage.getHeight());

        // Штрих-код
        g2d.drawImage(barcodeImage, 0, 0, null);

        // Текст под штрих-кодом
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));

        FontMetrics metrics = g2d.getFontMetrics();
        int textWidth = metrics.stringWidth(text);
        int x = (combinedImage.getWidth() - textWidth) / 2;
        int y = barcodeImage.getHeight() + 25; // Отступ от штрих-кода

        g2d.drawString(text, x, y);
        g2d.dispose();

        return combinedImage;
    }

    private static void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText("Ошибка создания штрих-кода");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText("Штрих-код создан");
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void ClientsShowInputDialog() throws SQLException, ClassNotFoundException {
        Stage dialog = new Stage();
        dialog.setTitle("КЛИЕНТЫ");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        // Создаем таблицу
        TableView<Client> table = new TableView<>();

        // Создаем колонки
        TableColumn<Client, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Client, String> nameColumn = new TableColumn<>("Имя");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Client, String> birthDateColumn = new TableColumn<>("Дата рождения");
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));

        TableColumn<Client, String> addressColumn = new TableColumn<>("Адрес");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Client, String> emailColumn = new TableColumn<>("E-Mail");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Client, String> telephoneColumn = new TableColumn<>("Телефон");
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        // Добавляем колонки в таблицу
        table.getColumns().addAll(idColumn, nameColumn, birthDateColumn, addressColumn, emailColumn, telephoneColumn);

        //=== ДОБАВЛЯЕМ ОБРАБОТЧИК ДВОЙНОГО КЛИКА ===
        table.setRowFactory(tv -> {
            TableRow<Client> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Client selectedClient = row.getItem();
                    handleClientSelection(selectedClient, dialog);
                }
            });
            return row;
        });

        // === ДОБАВЛЯЕМ ПОИСКОВУЮ СТРОКУ ===
        TextField searchField = new TextField();
        searchField.setPromptText("Поиск по имени или телефону...");
        searchField.setPrefWidth(300);

        // Создаем списки для фильтрации
        ObservableList<Client> originalClientsList = FXCollections.observableArrayList();
        FilteredList<Client> filteredClientsList = new FilteredList<>(originalClientsList);

        // Устанавливаем фильтрованные данные в таблицу
        table.setItems(filteredClientsList);

        // Настраиваем обработчик поиска
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateFilter(newValue, filteredClientsList);
        });

        Button addButton = new Button("ДОБАВИТЬ КЛИЕНТА");
        //Button refreshButton = new Button("ОБНОВИТЬ");
        Button allCancelButton = new Button("ЗАКРЫТЬ");
        allCancelButton.setOnAction(e -> {dialog.close();});

        addButton.setOnAction(e -> {AddClientsAdd(table, originalClientsList, searchField);});

        // Загружаем данные при открытии окна
        loadClientsFromDatabase(table, originalClientsList);

        // Создаем layout с поисковой строкой
        VBox mainLayout = new VBox(10);

        // Панель поиска
        HBox searchLayout = new HBox(10);
        searchLayout.getChildren().addAll(new Label("Поиск:"), searchField);

        // Панель кнопок
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(addButton, allCancelButton);

        // Добавляем все элементы в основной layout
        mainLayout.getChildren().addAll(searchLayout, table, buttonLayout);
        //mainLayout.setPadding(new Insets(10));

        Scene scene = new Scene(mainLayout, 1050, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }



    public void RefreshButton(TableView<Client> table, ObservableList<Client> originalClientsList, TextField searchField) throws SQLException, ClassNotFoundException {
                loadClientsFromDatabase(table, originalClientsList);
                //searchField.clear(); // Очищаем поиск при обновлении
    }


    // Метод для фильтрации таблицы
    private void updateFilter(String searchText, FilteredList<Client> filteredList) {
        if (searchText == null || searchText.isEmpty()) {
            filteredList.setPredicate(client -> true);
        } else {
            String lowerCaseFilter = searchText.toLowerCase();

            filteredList.setPredicate(client -> {
                // Поиск по имени (без учета регистра)
                if (client.getName() != null && client.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                // Поиск по телефону (без учета регистра)
                if (client.getTelephone() != null && client.getTelephone().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                // Дополнительный поиск по телефону (только цифры)
                if (client.getTelephone() != null) {
                    String phoneDigits = client.getTelephone().replaceAll("[^0-9]", "");
                    String searchDigits = lowerCaseFilter.replaceAll("[^0-9]", "");
                    if (!searchDigits.isEmpty() && phoneDigits.contains(searchDigits)) {
                        return true;
                    }
                }

                return false;
            });
        }
    }

    // Метод обработки выбора клиента
    private void handleClientSelection(Client client, Stage dialog) {
        //System.out.println("Выбран клиент: " + client.getName() + ", тел: " + client.getTelephone());
        id_client = client.getId();
        System.out.println(id_client);
    }


    // Метод для загрузки клиентов из базы данных
    private void loadClientsFromDatabase(TableView<Client> table, ObservableList<Client> originalList)
            throws SQLException, ClassNotFoundException {

        ObservableList<Client> clients = FXCollections.observableArrayList();

        ResultSet resultSet = dbConnector.getClients();
        while (resultSet.next()) {
            Client client = new Client(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("date_of_birth"),
                    resultSet.getString("address"),
                    resultSet.getString("e_mail"),
                    resultSet.getString("telephone")
            );
            clients.add(client);
        }

        // Обновляем оригинальный список
        originalList.setAll(clients);
    }


    public void Test(){System.out.println(id_client);}

    public void AddClientsAdd(TableView<Client> table, ObservableList<Client> originalClientsList, TextField searchField){
        Stage dialogs = new Stage();
        dialogs.setTitle("Добавить клиента");
        dialogs.initModality(Modality.APPLICATION_MODAL);
        dialogs.initOwner(primaryStage);

        TextField textName = new TextField();
        //TextField textDate_of_birth = new TextField();//data picker
        DatePicker datePicker = new DatePicker();
        TextField textAddress = new TextField();
        TextField textE_mail = new TextField();
        TextField textTelephone = new TextField();

        Button okButton = new Button("ДОБАВИТЬ");
        Button cancelButton = new Button("ЗАКРЫТЬ");
        okButton.setOnAction(d -> {
            String name = "";
            String date_of_birth = "";
            String address = "";
            String e_mail = "";
            String telephone = "";
            //Проверка на пустоту
            name = textName.getText();
            address = textAddress.getText();
            e_mail = textE_mail.getText();
            telephone = textTelephone.getText();

            if (datePicker.getValue() != null) {
                // Проверяем что дата не в будущем
                if (datePicker.getValue().isAfter(LocalDate.now())) {
                    Alert infoAlert = new Alert(Alert.AlertType.WARNING);
                    infoAlert.setTitle("Внимание");
                    infoAlert.setHeaderText("Ошибка");
                    infoAlert.setContentText("Дата не может быть в будущем");
                    infoAlert.showAndWait();
                    System.out.println("Неверная дата");
                    datePicker.setValue(null);
                }else {
                    // Проверяем что возраст больше 18 лет
                    if (datePicker.getValue().plusYears(18).isAfter(LocalDate.now())) {
                        Alert infoAlert = new Alert(Alert.AlertType.WARNING);
                        infoAlert.setTitle("Внимание");
                        infoAlert.setHeaderText("Ошибка");
                        infoAlert.setContentText("Возраст должен быть больше 18 лет");
                        infoAlert.showAndWait();
                        System.out.println("Возраст меньше 18 лет");
                        datePicker.setValue(null);
                    } else {
                        date_of_birth = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    }
                }
            }

            if (!(name.equals("") || date_of_birth.equals("") || address.equals("") || e_mail.equals("") || telephone.equals(""))){
                try {
                    ResultSet resultSet = dbConnector.getClientsId(name, date_of_birth, address, e_mail, telephone);
                    if (resultSet.next()){
                        Alert infoAlert = new Alert(Alert.AlertType.WARNING);
                        infoAlert.setTitle("Внимание");
                        infoAlert.setHeaderText("Запрос отклонен");
                        infoAlert.setContentText("Клиент есть в базе");
                        infoAlert.showAndWait();
                        System.out.println("Клиент существует");
                    }else {
                        try {
                            dbConnector.singUpClients(name, date_of_birth, address, e_mail, telephone);
                            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                            infoAlert.setTitle("Успех");
                            infoAlert.setHeaderText("Операция завершена");
                            infoAlert.setContentText("Клиент успешно добавлен");
                            infoAlert.showAndWait();
                            System.out.println("Внесен новый клиент в базу данных");
                            RefreshButton(table,  originalClientsList, searchField);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        /*try {
                            ResultSet resultSet_2 = dbConnector.getClientsId(name, date_of_birth, address, e_mail, telephone);
                            if (resultSet_2.next()) {
                                id_client = resultSet_2.getInt("id");
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }*/
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                Alert infoAlert = new Alert(Alert.AlertType.WARNING);
                infoAlert.setTitle("Внимание");
                infoAlert.setHeaderText("Пустое поле");
                infoAlert.setContentText("Вы ввели не все данные");
                infoAlert.showAndWait();
                System.out.println("Поле не может быть пустым");
            }

        });

        cancelButton.setOnAction(h -> {dialogs.close();});

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Label("Введите Имя:"),
                textName,
                new Label("ВВедите дату рождения:"),
                //textDate_of_birth,
                datePicker,
                new Label("ВВедите адрес:"),
                textAddress,
                new Label("Введите E-Mail:"),
                textE_mail,
                new Label("Введите телефон:"),
                textTelephone,
                okButton,
                cancelButton,
                new HBox(10)
        );

        Scene scene = new Scene(layout, 450, 400);
        dialogs.setScene(scene);
        dialogs.showAndWait();

    }

}






