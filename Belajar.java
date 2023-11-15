import javax.swing.*;
class buku{
public static void main (String[]args){
    JFrame f=new JFrame("data buku");
    JLabel kb= new JLabel("kode buku");
    JLabel jb= new JLabel("judul buku");
    JLabel si= new JLabel("sinopsis");
    JLabel pg= new JLabel("pengarang");
    JLabel kt= new JLabel ("kategori");
    JTextField tkode=new JTextField();
    JTextField tjudul=new JTextField();
    JTextArea aSin=new JTextArea();
    JTextField tpengarang=new JTextField();
    String [] kategori = {"komputer","kesehatan","kuliner"};
    JComboBox kat=new JComboBox(kategori);
    JButton bsimpan=new JButton ("simpan");
    JButton bkeluar= new JButton ("keluar");
    kb.setBounds(10,10,100,20);
    jb.setBounds(10,40,100,20);
    si.setBounds(10,70,100,70);
    pg.setBounds(10,150,100,20);
    kt.setBounds(10,180,100,20);
    tkode.setBounds(110,10,80,20);
    tjudul.setBounds(110,40,140,20);
    aSin.setBounds(110,70,100,70);
    tpengarang.setBounds(110,150,100,20);
    kat.setBounds(110,180,90,20);
    bsimpan.setBounds(110,210,100,40);
    bkeluar.setBounds(220,210,100,40);
    
    f.add(kb);
    f.add(jb);
    f.add(si);
    f.add(pg);
    f.add(kt);
    f.add(tkode);
    f.add(tjudul);
    f.add(aSin);
    f.add(tpengarang);
    f.add(kat);
    f.add(bsimpan);
    f.add(bkeluar);
    f.setSize(400,300);
    f.setLayout(null);
    f.setVisible(true);

    
    
    
            
   }
}