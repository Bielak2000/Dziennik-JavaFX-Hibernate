public class KlasaPomocnicza {
    @AsTableColumn(text="Ocena")
    public double ocena;

    KlasaPomocnicza(double a){
        ocena=a;
    }

    public double getOcena(){
        return ocena;
    }
}
