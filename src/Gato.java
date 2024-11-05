import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gato extends JLabel {
    private JPanel panelContainer;
    private CardLayout cardLayout;
    private JPanel cenarioPrincipal; 
    private Image imagemMissoes; 

    public Gato(JPanel cenarioPrincipal, JPanel panelContainer, CardLayout cardLayout) {
        this.cenarioPrincipal = cenarioPrincipal;
        this.panelContainer = panelContainer;
        this.cardLayout = cardLayout;
        setOpaque(false);  
        setPreferredSize(new Dimension(120, 120)); 
        setBounds(100, 100, 120, 190);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPainelMissoes();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        cenarioPrincipal.add(this);  
    }

    private void abrirPainelMissoes() {
        JPanel painelMissoes = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                imagemMissoes = new ImageIcon(getClass().getResource("/resources/imagens/missoes.png")).getImage();

                g.drawImage(imagemMissoes, 0, 0, getWidth(), getHeight(), this);
            }
        };

        painelMissoes.setLayout(null);

        JLabel missao1 = new JLabel("• Incluir 3 medicamentos para Anisaquíase suprema");
        missao1.setForeground(new Color(0, 100, 0)); 
        missao1.setBounds(50, 130, 400, 30);
        painelMissoes.add(missao1);

        JLabel missao2 = new JLabel("• Assistir a uma aula de imunologia");
        missao2.setForeground(new Color(0, 100, 0)); 
        missao2.setBounds(50, 150, 400, 30); 
        painelMissoes.add(missao2);

        JLabel missao3 = new JLabel("• Por fim, colete os dois vermes do gênero Anisakis no laboratório");
        missao3.setForeground(new Color(0, 100, 0)); 
        missao3.setBounds(50, 170, 500, 30);
        painelMissoes.add(missao3);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(20, 420, 80, 20);
        botaoVoltar.addActionListener(e -> {
            cardLayout.show(panelContainer, "cenarioPrincipal");
            cenarioPrincipal.requestFocusInWindow();
        });

        painelMissoes.add(botaoVoltar);

        panelContainer.add(painelMissoes, "painelMissoes");
        cardLayout.show(panelContainer, "painelMissoes");
    }
}