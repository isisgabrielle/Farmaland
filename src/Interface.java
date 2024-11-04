import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Interface {
    private JPanel panelContainer;
    private JPanel panelPrincipal;
    private JPanel cenarioPrincipal;
    private JPanel novoPanel;
    private JButton botaoEntrar;
    private JButton sairButton;
    private Image imagemFundo;
    private Image cenarioFundo;
    private CardLayout cardLayout; 
    private JLabel coordenadasLabel; // Rótulo para coordenadas

    public Interface() {
        imagemFundo = new ImageIcon("imagens/FarmaLand.png").getImage(); 
        cenarioFundo = new ImageIcon("imagens/cenarioPrincipal.png").getImage();
        createUIComponents();
    }

    private void createUIComponents() {
        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

        // Instancia a classe Laboratorio passando os parâmetros necessários
        Laboratorio laboratorio = new Laboratorio(cenarioPrincipal, cardLayout);

        panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagemFundo != null) {
                    g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelPrincipal.setLayout(null); 

        cenarioPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (cenarioFundo != null) {
                    g.drawImage(cenarioFundo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Configura o cenarioPrincipal como focável e solicita foco
        cenarioPrincipal.setFocusable(true);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(172, 320, 86, 29);
        botaoEntrar.addActionListener(e -> {
            cardLayout.show(panelContainer, "cenarioPrincipal");
            cenarioPrincipal.requestFocusInWindow();  // Solicita foco no painel ao entrar no cenário
        });

        sairButton = new JButton("Sair");
        sairButton.setBounds(302, 320, 86, 29); 
        sairButton.addActionListener(e -> System.exit(0));

        panelPrincipal.add(botaoEntrar);
        panelPrincipal.add(sairButton);

        panelContainer.add(panelPrincipal, "panelPrincipal");
        panelContainer.add(cenarioPrincipal, "cenarioPrincipal");
        cenarioPrincipal.setLayout(null);

        Computador computadorClicavel = new Computador(cenarioPrincipal, panelContainer, cardLayout);
        computadorClicavel.setBounds(58, 261, 77, 81);
        cenarioPrincipal.add(computadorClicavel);

        // Instancia o personagem e adiciona ao painel
        PersonagemEllo personagem = new PersonagemEllo();
        personagem.setBounds(130, 150, 180, 180); // Ajuste para as dimensões do personagem
        cenarioPrincipal.add(personagem);

        // Rótulo para exibir as coordenadas do personagem
        coordenadasLabel = new JLabel("Coordenadas: (130, 150)");
        coordenadasLabel.setBounds(10, 10, 200, 30);
        cenarioPrincipal.add(coordenadasLabel);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(512, 49, 62, 138); // Define a posição e o tamanho da JLabel
        cenarioPrincipal.add(lblNewLabel); // Adiciona a JLabel ao painel principal

        // Adiciona o MouseListener à JLabel para responder ao clique
        lblNewLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                laboratorio.criarLab(); // Chama a função criarLab() da instância de Laboratorio
            }
        });

        // Adiciona o KeyListener ao cenarioPrincipal para movimentar o personagem
        cenarioPrincipal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int x = personagem.getX();
                int y = personagem.getY();

                // Define os limites de movimento
                int limiteEsquerdo = -70;
                int limiteDireito = 470;
                int limiteCima = 50;
                int limiteBaixo = 320;

                // Atualiza a posição do personagem, respeitando os limites
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

                // Verifica se a nova posição está nas áreas bloqueadas
                if ((personagem.getX() == 0 && personagem.getY() >= 150 && personagem.getY() <= 280) || 
                    (personagem.getX() > -60 && personagem.getX() <= 50 && personagem.getY() >= 150 && personagem.getY() <= 280)) {
                    // Se o personagem estiver em uma área bloqueada, move de volta para a posição anterior
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT -> personagem.setLocation(x, y); // volta para a posição anterior
                        case KeyEvent.VK_LEFT -> personagem.setLocation(x, y); // volta para a posição anterior
                        case KeyEvent.VK_UP -> personagem.setLocation(x, y); // volta para a posição anterior
                        case KeyEvent.VK_DOWN -> personagem.setLocation(x, y); // volta para a posição anterior
                    }
                }
             
                coordenadasLabel.setText("Coordenadas: (" + personagem.getX() + ", " + personagem.getY() + ")");
                if (personagem.getX() >= 420 && personagem.getY()>= 50) {
                    novoPanel = new JPanel();
                    novoPanel.setBackground(Color.LIGHT_GRAY);
                    novoPanel.add(new JLabel("Você desbloqueou um novo painel!"));
                    
                    panelContainer.add(novoPanel, "novoPanel");
                }
            } 
        });

        JFrame frame = new JFrame("FarmaEllo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panelContainer); 
        frame.setSize(600, 500); 
        frame.setVisible(true); 
        frame.setResizable(false);

        cardLayout.show(panelContainer, "panelPrincipal");
    }

    public static void main(String[] args) {
        new Interface();
    }
}
