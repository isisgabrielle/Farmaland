import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PersonagemEllo extends JLabel {
    private ImageIcon personagemEllo;
    private int posicaoX = 0; // posição inicial do personagem
    private int posicaoY = 0; // posição inicial do personagem

    public PersonagemEllo() {
        personagemEllo = new ImageIcon("imagens/elloPersonagem.png");
        Image img = personagemEllo.getImage();
        int largura = 180;
        int altura = 180;
        Image newImg = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

        personagemEllo = new ImageIcon(newImg);
        setIcon(personagemEllo);
        setPreferredSize(new Dimension(largura, altura));
        setSize(largura, altura);

        // Define a posição inicial do personagem
        setLocation(posicaoX, posicaoY);
    }

    // Métodos para movimentação
    public void moverParaDireita() {
        posicaoX += 10; // ajusta o valor para a distância que quer mover
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
