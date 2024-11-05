import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Laboratorio extends JLabel {
    private JPanel panelContainer;
    private CardLayout cardLayout;
    private Image imagemLaboratorio;  

    public Laboratorio(JPanel cenarioPrincipal, JPanel panelContainer, CardLayout cardLayout) {
        this.panelContainer = panelContainer;
        this.cardLayout = cardLayout;
        setOpaque(false);  
        setPreferredSize(new Dimension(120, 120)); 
        setBounds(100, 100, 120, 190);

    
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPainelLaboratorio();
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

    private void abrirPainelLaboratorio() {
        JPanel painelLaboratorio = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                imagemLaboratorio = new ImageIcon(getClass().getResource("/resources/imagens/laboratorio.png")).getImage();
                g.drawImage(imagemLaboratorio, 0, 0, getWidth(), getHeight(), this);
            }
        };

        painelLaboratorio.setLayout(null);


        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(20, 20, 80, 20);
        botaoVoltar.addActionListener(e -> cardLayout.show(panelContainer, "cenarioPrincipal"));
        painelLaboratorio.add(botaoVoltar);

        PersonagemEllo personagem = new PersonagemEllo();
        personagem.setBounds(-70, 280, 180, 180);
        painelLaboratorio.add(personagem);

        JLabel coordenadasLabel = new JLabel("Coordenadas: (-70, 250)");
        coordenadasLabel.setBounds(10, 10, 200, 30);
        painelLaboratorio.add(coordenadasLabel);

        JLabel verme1 = criadorDeVerme("/resources/imagens/vermes.png", 270, 390, painelLaboratorio);
        JLabel verme2 = criadorDeVerme("/resources/imagens/vermes.png", 420, 390, painelLaboratorio);
        
        JButton botaoSair = new JButton("Sair");
        botaoSair.setBounds(20, 50, 80, 20);
        botaoSair.addActionListener(e -> {
            int resposta = JOptionPane.showConfirmDialog(painelLaboratorio, "Você realmente deseja sair do jogo?", "Sair", JOptionPane.YES_NO_OPTION);
            if (resposta == JOptionPane.YES_OPTION) {
                System.exit(0); }
        });
        botaoSair.setVisible(false); 
        painelLaboratorio.add(botaoSair);
        
        painelLaboratorio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int x = personagem.getX();
                int y = personagem.getY();

                int limiteEsquerdo = -70;
                int limiteDireito = 470;
                int limiteCima = 280;
                int limiteBaixo = 280;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_RIGHT -> {
                        if (x < limiteDireito) personagem.setLocation(x + 10, y);
                    }
                    case KeyEvent.VK_LEFT -> {
                        if (x > limiteEsquerdo) personagem.setLocation(x - 10, y);
                    }
                    case KeyEvent.VK_UP -> {
                        if (y > limiteCima) personagem.setLocation(x, y - 10);
                    }
                    case KeyEvent.VK_DOWN -> {
                        if (y < limiteBaixo) personagem.setLocation(x, y + 10);
                    }
                }

                coordenadasLabel.setText("Coordenadas: (" + personagem.getX() + ", " + personagem.getY() + ")");
                
                verificarColisao(personagem, verme1, painelLaboratorio);
                verificarColisao(personagem, verme2, painelLaboratorio);

                if (personagem.getX() == 320 && personagem.getY() == 280) {
                    JOptionPane.showMessageDialog(painelLaboratorio, "Parabéns, você venceu!"); 
                    botaoSair.setVisible(true);
                }
            }
        });

        painelLaboratorio.setFocusable(true);
        painelLaboratorio.requestFocusInWindow();
        panelContainer.add(painelLaboratorio, "painelLaboratorio");
        cardLayout.show(panelContainer, "painelLaboratorio");
        painelLaboratorio.requestFocusInWindow();
    }
    
    private JLabel criadorDeVerme(String caminhoImagem, int x, int y, JPanel painel) {
        ImageIcon imagem = new ImageIcon(getClass().getResource(caminhoImagem));
        Image img = imagem.getImage();
        int largura = 50; 
        int altura = 50; 
        Image newImg = img.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        imagem = new ImageIcon(newImg); 
        
        JLabel verme = new JLabel(imagem);
        verme.setBounds(x, y, largura, altura);
        painel.add(verme);
        return verme;
    }

    private void verificarColisao(JLabel personagem, JLabel verme, JPanel painel) {
        Rectangle personagemBounds = personagem.getBounds();
        Rectangle vermeBounds = verme.getBounds();

        if (personagemBounds.intersects(vermeBounds)) {
            painel.remove(verme);
            painel.repaint();
        }
    }
}
