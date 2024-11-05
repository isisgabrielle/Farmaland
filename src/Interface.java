import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class Interface {
    private JPanel panelContainer;
    private JPanel panelPrincipal;
    private JPanel panelHistoria;
    private JPanel panelRegras;
    private JPanel cenarioPrincipal;
    private JButton botaoEntrar;
    private CardLayout cardLayout;
    private JLabel timerLabel;
    private Timer timer;
    private int tempoRestante = 600;
    private JButton sairButton;
    private Image imagemFundo;
    private Image cenarioFundo;
 
    public Interface() {
        createUIComponents();
        imagemFundo = new ImageIcon(getClass().getResource("/resources/imagens/FarmaLand.png")).getImage();
        cenarioFundo = new ImageIcon(getClass().getResource("/resources/imagens/cenarioPrincipal.png")).getImage();
    }

    private void createUIComponents() {
        cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);
        panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagemFundo = new ImageIcon(getClass().getResource("/resources/imagens/FarmaLand.png")).getImage();
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelPrincipal.setLayout(null);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(220, 396, 86, 29);
        botaoEntrar.addActionListener(e -> cardLayout.show(panelContainer, "panelHistoria"));
        panelPrincipal.add(botaoEntrar);

        JButton sairButton = new JButton("Sair");
        sairButton.setBounds(329, 396, 86, 29);
        sairButton.addActionListener(e -> System.exit(0));
        panelPrincipal.add(sairButton);

        panelContainer.add(panelPrincipal, "panelPrincipal");

        panelHistoria = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagemHistoria = new ImageIcon(getClass().getResource("/resources/imagens/historiaJogo.png")).getImage();
                g.drawImage(imagemHistoria, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelHistoria.setLayout(null);
        JButton botaoProximoHistoria = new JButton("Próximo");
        botaoProximoHistoria.setBounds(430, 420, 100, 25);
        botaoProximoHistoria.addActionListener(e -> cardLayout.show(panelContainer, "panelRegras"));
        panelHistoria.add(botaoProximoHistoria);
        panelContainer.add(panelHistoria, "panelHistoria");

  
        panelRegras = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image imagemRegras = new ImageIcon(getClass().getResource("/resources/imagens/regrasJogo.png")).getImage();
                g.drawImage(imagemRegras, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelRegras.setLayout(null);
        JButton botaoProximoRegras = new JButton("Próximo");
        botaoProximoRegras.setBounds(430, 420, 100, 25);
        botaoProximoRegras.addActionListener(e -> {
            cardLayout.show(panelContainer, "cenarioPrincipal");
            cenarioPrincipal.requestFocusInWindow();
            iniciarTimer();
        });
        panelRegras.add(botaoProximoRegras);
        panelContainer.add(panelRegras, "panelRegras");


         cenarioPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image cenarioFundo = new ImageIcon(getClass().getResource("/resources/imagens/cenarioPrincipal.png")).getImage();
                if (cenarioFundo != null) {
                    g.drawImage(cenarioFundo, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        cenarioPrincipal.setLayout(null);  
        cenarioPrincipal.setFocusable(true);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(220, 396, 86, 29);
        botaoEntrar.addActionListener(e -> {
            cardLayout.show(panelContainer, "cenarioPrincipal");
            cenarioPrincipal.requestFocusInWindow();
            iniciarTimer();
        });
        panelPrincipal.add(botaoEntrar);

        sairButton = new JButton("Sair");
        sairButton.setBounds(329, 396, 86, 29);
        sairButton.addActionListener(e -> System.exit(0));
        panelPrincipal.add(sairButton);

        panelContainer.add(panelPrincipal, "panelPrincipal");
        panelContainer.add(cenarioPrincipal, "cenarioPrincipal");

        timerLabel = new JLabel("Tempo: 10:00");
        timerLabel.setBounds(475, 10, 100, 30);
        cenarioPrincipal.add(timerLabel);

        PersonagemEllo personagem = new PersonagemEllo();
        personagem.setBounds(130, 150, 180, 180); 
        cenarioPrincipal.add(personagem);

        Computador computadorClicavel = new Computador(cenarioPrincipal, panelContainer, cardLayout);
        
        cenarioPrincipal.add(computadorClicavel);

        Laboratorio laboratorioClicavel = new Laboratorio(cenarioPrincipal, panelContainer, cardLayout);
        laboratorioClicavel.setBounds(512, 49, 62, 138);  
        cenarioPrincipal.add(laboratorioClicavel);

        Gato gatoChao = new Gato(cenarioPrincipal, panelContainer, cardLayout);
        gatoChao.setBounds(469, 425, 56, 25);
        cenarioPrincipal.add(gatoChao);

        Livro livroClicavel = new Livro(cenarioPrincipal, panelContainer, cardLayout);
        livroClicavel.setBounds(78, 353, 46, 25);
        cenarioPrincipal.add(livroClicavel);

        cenarioPrincipal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int x = personagem.getX();
                int y = personagem.getY();
                int limiteEsquerdo = -70;
                int limiteDireito = 470;
                int limiteCima = 50;
                int limiteBaixo = 320;
              
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

       
                if ((personagem.getX() == 0 && personagem.getY() >= 150 && personagem.getY() <= 280) ||
                    (personagem.getX() > -60 && personagem.getX() <= 50 && personagem.getY() >= 150 && personagem.getY() <= 280)) {
              
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN ->
                            personagem.setLocation(x, y); 
                    }
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

    private void iniciarTimer() {
        timer = new Timer(1000, e -> {
            tempoRestante--;
            int minutos = tempoRestante / 60;
            int segundos = tempoRestante % 60;
            timerLabel.setText(String.format("Tempo: %02d:%02d", minutos, segundos));

            if (tempoRestante <= 0) {
                timer.stop();
                JOptionPane.showMessageDialog(panelContainer, "Você perdeu!");
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        new Interface();
    }
}