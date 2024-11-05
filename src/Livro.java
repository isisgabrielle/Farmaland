import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Livro extends JLabel {
    private JPanel panelContainer;
    private CardLayout cardLayout;
    private JPanel cenarioPrincipal;
    private Image imagemLivro;  

    public Livro(JPanel cenarioPrincipal, JPanel panelContainer, CardLayout cardLayout) {
        this.cenarioPrincipal = cenarioPrincipal; 
        this.panelContainer = panelContainer;
        this.cardLayout = cardLayout;

        setOpaque(false);  
        setPreferredSize(new Dimension(120, 120));
        setBounds(300, 100, 120, 190); 

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPainelLivro();
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

   
    private void abrirPainelLivro() {
        JPanel painelLivro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                imagemLivro = new ImageIcon(getClass().getResource("/resources/imagens/livro.png")).getImage();

                g.drawImage(imagemLivro, 0, 0, getWidth(), getHeight(), this);
            }
        };

        painelLivro.setLayout(null);

     
        JButton botaoEstudar = new JButton("Vamos estudar imunologia");
        botaoEstudar.setBounds(180, 230, 200, 30);
        botaoEstudar.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI("https://www.youtube.com/watch?v=Q9KvcdDPL5M"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        painelLivro.add(botaoEstudar);

      
        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(20, 420, 80, 20);
        botaoVoltar.addActionListener(e -> {
            cardLayout.show(panelContainer, "cenarioPrincipal");
            cenarioPrincipal.requestFocusInWindow(); 
        });
        painelLivro.add(botaoVoltar);

        panelContainer.add(painelLivro, "painelLivro");
        cardLayout.show(panelContainer, "painelLivro");
    }
}