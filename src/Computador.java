import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Computador extends JLabel {
    private Image imagemComputador;
    private Image imagemSistema;
    private Image imagemBotaoVoltar;
    private JPanel cenarioPrincipal;
    private JPanel panelContainer;
    private CardLayout cardLayout;
    private JButton incluirMedicamento;
    private JButton excluirMedicamento;
    private JButton listarMedicamentos;
    private JButton alterarMedicamentos;
    private JButton botaoVoltar;
    private String nomeDoMedicamento;
    private int estoque;

    public Computador(JPanel cenarioPrincipal, JPanel panelContainer, CardLayout cardLayout) {
        this.cenarioPrincipal = cenarioPrincipal;
        this.panelContainer = panelContainer;
        this.cardLayout = cardLayout;
        imagemComputador =  new ImageIcon(getClass().getResource("/resources/imagens/computador.png")).getImage();
        imagemSistema = new ImageIcon(getClass().getResource("/resources/imagens/fundoSistema.png")).getImage();
        imagemBotaoVoltar = new ImageIcon(getClass().getResource("/resources/imagens/voltarPixel.png")).getImage();
      
        int largura = 120;
        int altura = 120;
        setPreferredSize(new Dimension(largura, altura));
        setSize(95, 113);
        setLocation(25, 226);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                criarPaineis();
            }
        });

        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagemComputador != null) {
            g.drawImage(imagemComputador, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void criarPaineis() {
        JPanel painelSistema = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagemSistema != null) {
                    g.drawImage(imagemSistema, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        painelSistema.setLayout(null);

        incluirMedicamento = new JButton("Incluir Medicamento");
        incluirMedicamento.setBounds(120, 210, 165, 30);
        incluirMedicamento.addActionListener(e -> {
            String nomeDoMedicamento = JOptionPane.showInputDialog("Insira o nome do medicamento:");
            if (nomeDoMedicamento == null || nomeDoMedicamento.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nome do medicamento não pode ser vazio!");
                return;
            }
            int estoque = Integer.parseInt(JOptionPane.showInputDialog("Insira a quantidade em estoque:"));

            IncluirMedicamento incluir = new IncluirMedicamento();
            incluir.escreverArquivo(nomeDoMedicamento, estoque);
        });

        painelSistema.add(incluirMedicamento);
        
        alterarMedicamentos = new JButton("Alterar medicamento");
        alterarMedicamentos.setBounds(120, 250, 165, 30);
        alterarMedicamentos.addActionListener(e -> {
            String nomeDoMedicamento = JOptionPane.showInputDialog("Digite o nome do medicamento a ser alterado:");
            
            if (nomeDoMedicamento != null && !nomeDoMedicamento.isEmpty()) {
                try {
                    int novoEstoque = Integer.parseInt(JOptionPane.showInputDialog("Digite a nova quantidade em estoque:"));
                    IncluirMedicamento incluir = new IncluirMedicamento();
                
                    boolean encontrado = incluir.alterarMedicamentos(nomeDoMedicamento, novoEstoque);
                    
                    if (encontrado) {
                        JOptionPane.showMessageDialog(null, "Estoque do medicamento alterado com sucesso!");} 
                    else {
                        JOptionPane.showMessageDialog(null, "Medicamento não encontrado na base de dados!");}
                } 
                catch (NumberFormatException ex) {
                       JOptionPane.showMessageDialog(null, "Por favor, insira um número válido para o estoque."); }
            }
        });
        painelSistema.add(alterarMedicamentos);

        excluirMedicamento = new JButton("Excluir Medicamento");
        excluirMedicamento.setBounds(300, 210, 165, 30);
        excluirMedicamento.addActionListener(e -> {
            String nomeDoMedicamento = JOptionPane.showInputDialog("Digite o nome do medicamento a ser excluído:");
            if (nomeDoMedicamento != null && !nomeDoMedicamento.isEmpty()) {
                IncluirMedicamento incluir = new IncluirMedicamento();
                incluir.excluirMedicamento(nomeDoMedicamento);
            } else {
                JOptionPane.showMessageDialog(null, "Nome do medicamento não pode ser vazio!");
            }
        });

        painelSistema.add(excluirMedicamento);
        
        JButton listarMedicamentos = new JButton("Listar Medicamentos");
        listarMedicamentos.setBounds(300, 250, 165, 30);
        listarMedicamentos.addActionListener(e -> {
            IncluirMedicamento incluir = new IncluirMedicamento();
            incluir.listarMedicamentos();
        });
        painelSistema.add(listarMedicamentos);

        Image img = imagemBotaoVoltar;
        int larguraVoltar = 120;
        int alturaVoltar = 120;
        Image newImg = img.getScaledInstance(larguraVoltar, alturaVoltar, Image.SCALE_SMOOTH);
        setPreferredSize(new Dimension(larguraVoltar, alturaVoltar));
        setSize(larguraVoltar, alturaVoltar);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setBounds(20, 400, 100, 40);
        botaoVoltar.addActionListener(e ->{ cardLayout.show(panelContainer, "cenarioPrincipal");
        cenarioPrincipal.requestFocusInWindow();
       });
        
        painelSistema.add(botaoVoltar);
        panelContainer.add(painelSistema, "painelSistema");
        cardLayout.show(panelContainer, "painelSistema");
    }
}