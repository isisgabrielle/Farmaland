import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class IncluirMedicamento{
	private static final String ARQUIVO_MEDICAMENTOS = "medicamentos.txt"; 
	private boolean encontrado;
	
	public boolean verificarMedicamento(String nomeDoMedicamento) {
        File arquivo = new File("medicamentos" + ".txt");

        if (!arquivo.exists()) {
         
            return false;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                if (linha.split(" ")[0].equalsIgnoreCase(nomeDoMedicamento)) {
                    return true; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false; 
    }
		public void escreverArquivo(String nomeDoMedicamento, int estoque) {

		    if (verificarMedicamento(nomeDoMedicamento)) {
		    	JOptionPane.showMessageDialog(null, "o medicamento já estava cadastrado!");
		    } else {
		        auxiliarEscreverArquivo("medicamentos" + ".txt", nomeDoMedicamento + " " + estoque);
		        JOptionPane.showMessageDialog(null, "Medicamento incluído com sucesso!");
		    }
		}

	
	private static void auxiliarEscreverArquivo(String caminho, String textoArq ){
	try(
		FileWriter criadorArquivos = new FileWriter(caminho,true);
		BufferedWriter buffer = new BufferedWriter(criadorArquivos);
		PrintWriter escritorArquivos = new PrintWriter(buffer); ){
		escritorArquivos.println(textoArq);
		} 
	
	catch(IOException e) {
		e.printStackTrace();}}
	
	 

	public void excluirMedicamento(String nomeDoMedicamento) {
        File arquivoOriginal = new File(ARQUIVO_MEDICAMENTOS);
        File arquivoTemporario = new File("medicamentos_temp.txt");

        try (
            BufferedReader leitor = new BufferedReader(new FileReader(arquivoOriginal));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoTemporario))
        ) {
            String linha;
            boolean encontrado = false;

            while ((linha = leitor.readLine()) != null) {
                String[] dados = linha.split(" ");
                String nome = dados[0];

                if (!nome.equalsIgnoreCase(nomeDoMedicamento)) {
                    escritor.write(linha);
                    escritor.newLine();
                } else {
                    encontrado = true;
                }
            }

            // Fechar o escritor após a leitura e escrita
            escritor.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (arquivoOriginal.delete()) {
            if (arquivoTemporario.renameTo(arquivoOriginal)) {
                System.out.println("Medicamento " + nomeDoMedicamento + " excluído com sucesso.");
            } else {
                System.out.println("Erro ao renomear o arquivo temporário.");
            }
        } else {
            System.out.println("Erro ao deletar o arquivo original.");
        }

  
        
    }

	public boolean alterarMedicamentos(String nomeDoMedicamento, int novoEstoque) {
	    File arquivoOriginal = new File("medicamentos.txt");
	    File arquivoTemporario = new File("medicamentos_temp.txt");
	    boolean encontrado = false;

	    try (
	        BufferedReader leitor = new BufferedReader(new FileReader(arquivoOriginal));
	        BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivoTemporario))
	    ) {
	        String linha;

	        while ((linha = leitor.readLine()) != null) {
	            String[] dados = linha.split(" ");
	            String nome = dados[0];
	            int estoque = Integer.parseInt(dados[1]);

	            if (nome.equalsIgnoreCase(nomeDoMedicamento)) {

	                estoque = novoEstoque;
	                encontrado = true;
	            }

	            escritor.write(nome + " " + estoque);
	            escritor.newLine();
	        }

	        escritor.flush();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    if (arquivoOriginal.delete() && arquivoTemporario.renameTo(arquivoOriginal)) {
	        return encontrado;
	    } else {
	        System.out.println("Erro ao atualizar o arquivo de medicamentos.");
	        return false;
	    }
	}
	  public void listarMedicamentos() {
	        StringBuilder listaMedicamentos = new StringBuilder();

	        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_MEDICAMENTOS))) {
	            String linha;
	            while ((linha = leitor.readLine()) != null) {
	                listaMedicamentos.append(linha).append("\n");
	            }
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo de medicamentos.");
	            e.printStackTrace();
	            return;
	        }

	        if (listaMedicamentos.length() == 0) {
	            JOptionPane.showMessageDialog(null, "Nenhum medicamento encontrado.");
	        } else {
	            JTextArea textArea = new JTextArea(listaMedicamentos.toString());
	            textArea.setEditable(false);
	            JScrollPane scrollPane = new JScrollPane(textArea);
	            scrollPane.setPreferredSize(new Dimension(400, 300));
	            JOptionPane.showMessageDialog(null, scrollPane, "Lista de Medicamentos", JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	
	
	
	}
