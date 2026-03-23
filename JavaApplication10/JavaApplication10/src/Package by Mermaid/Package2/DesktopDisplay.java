package Package2;

public class DesktopDisplay implements DisplayPlatform {

    @Override
    public void display(String content) {
        System.out.println("==============================================");
        System.out.println("   [ Desktop Display - Medical Records ]     ");
        System.out.println("==============================================");
        System.out.println(content);
        System.out.println("==============================================");
        System.out.println("[Desktop Display]: Record displayed successfully on Desktop.");
    }
}
