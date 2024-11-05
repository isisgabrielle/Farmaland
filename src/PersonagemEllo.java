import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PersonagemEllo extends JLabel {
    private Image personagemEllo;
    private int posicaoX = 0; 
    private int posicaoY = 0; 

    public PersonagemEllo() {
        personagemEllo = new ImageIcon(getClass().getResource("/resources/imagens/elloPersonagem.png")).getImage();

        Image img = personagemEllo;
        int largura = 180;
        int altura = 180;
        Image newImg = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

        setPreferredSize(new Dimension(largura, altura));
        setSize(largura, altura);
        setLocation(posicaoX, posicaoY);
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (personagemEllo!= null) {
            g.drawImage(personagemEllo, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void moverParaDireita() {
        posicaoX += 10; 
        setLocation(posicaoX, posicaoY);
    }

    public void moverParaEsquerda() {
        posicaoX -= 10;
        setLocation(posicaoX, posicaoY);
    }

    public void moverParaCima() {
        posicaoY -= 10;
        setLocation(posicaoX, posicaoY);
    }

    public void moverParaBaixo() {
        posicaoY += 10;
        setLocation(posicaoX, posicaoY);
    }
}
