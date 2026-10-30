import controller.CarteiraController;

public class App {
    public static void main(String[] args) {
        CarteiraController controller = CarteiraController.criarPadrao();

        controller.iniciar();
    }
}
