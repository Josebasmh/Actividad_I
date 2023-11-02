package application;
	
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Propiedades;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;


public class Main extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("PERSONA");
		
		//Image imgLogo = new Image();
		FlowPane root;
		try {
			String idioma = Propiedades.getValor("idioma");
			String region = Propiedades.getValor("region");
			Locale.setDefault(new Locale(idioma,region));
			ResourceBundle bundle= ResourceBundle.getBundle("idiomas/messages");
			root = (FlowPane)FXMLLoader.load(getClass().getResource("/fxml/Persona.fxml"),bundle);
			Scene scene = new Scene(root,820,620);
			stage.setScene(scene);
			stage.setMinHeight(620);
			stage.setMinWidth(820);
			stage.show();
		} catch (IOException e) {
			System.out.println("La ventana no se abrió correctamente.");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
