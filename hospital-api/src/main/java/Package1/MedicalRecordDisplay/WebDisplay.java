package Package1.MedicalRecordDisplay;

public class WebDisplay implements DisplayPlatform {

    @Override
    public void display(String content) {
        System.out.println("==============================================");
        System.out.println("   [ Web Display - Medical Records ]         ");
        System.out.println("==============================================");
        System.out.println(content);
        System.out.println("==============================================");
        System.out.println("[Web Display]: Record displayed successfully on Web.");
    }
}
