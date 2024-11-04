import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Laboratorio extends JPanel {
    private ImageIcon laboratorio;
    private JPanel panelContainer;
    private CardLayout cardLayout;

    public Laboratorio(JPanel panelContainer, CardLayout cardLayout) {
        this.panelContainer = panelContainer;
        this.cardLayout = cardLayout;
        
        laboratorio = new ImageIcon("imagens/laboratorio.png");

        // Redimensionar a imagem
        Image img = laboratorio.getImage();
        int largura = 120;
        int altura = 120;
        Image newImg = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

        laboratorio = new ImageIcon(newImg);

        setPreferredSize(new Dimension(largura, altura));
        setSize(largura, altura);

        // Adiciona um ouvinte de clique na label
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                criarLab();
            }
        });

        setCursor(new Cursor(Cursor.HAND_CURSOR)); // Define o cursor como mão
    }

    // Função para criar e exibir o painel do laboratório
    public void criarLab() {
        // Cria o painel do laboratório
        JPanel painelLaboratorio = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (laboratorio != null) {
                    g.drawImage(laboratorio.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        painelLaboratorio.setLayout(null);  // Define layout absoluto para o novo painel

        // Adiciona o novo painel ao panelContainer e troca para ele
        panelContainer.add(painelLaboratorio, "painelLaboratorio");
        cardLayout.show(panelContainer, "painelLaboratorio"); // Troca para o novo painel
    }
}
