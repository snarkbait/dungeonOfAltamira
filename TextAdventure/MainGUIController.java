/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author tim
 */
public class MainGUIController implements Initializable {
    @FXML
    private Button saveButton;
    @FXML
    private Button quitButton;
    @FXML
    private Label playerName;
    @FXML
    private TextField inputBox;
    @FXML
    private TextArea console;
    @FXML
    private ImageView imageViewer;
    @FXML
    private ProgressBar healthBar;
    @FXML
    private ProgressBar hungerBar;
    @FXML
    private Label labelHP;
    @FXML
    private Label labelExp;
    @FXML
    private Label labelAC;
    @FXML
    private Label labelLevel;
    @FXML
    private Label labelToHit;
    @FXML
    private Label labelGold;
    @FXML
    private ListView<?> invListView;
    @FXML
    private Button equipButton;
    @FXML
    private Button dropButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
