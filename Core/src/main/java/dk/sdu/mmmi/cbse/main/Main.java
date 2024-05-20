package dk.sdu.mmmi.cbse.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnnotationConfigApplicationContext AnnoConAppCtx = new AnnotationConfigApplicationContext(ModuleConfig.class);
        Game game = AnnoConAppCtx.getBean(Game.class);
        game.start(stage);
        game.render();
    }
}
