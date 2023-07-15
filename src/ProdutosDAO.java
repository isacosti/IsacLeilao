
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public boolean conectar() { // método que vai fazer a conexão com o banco de dados 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/uc11?useSSL=false", "root", "81397848");
            System.out.println("Conectado");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return false;
        }
    }

    public void desconectar() { // método que vai desconectar do banco de dados 
        try {
            conn.close();
            System.out.println("Desconectado!");
        } catch (SQLException ex) {

        }
    }

    public void cadastrarProduto(ProdutosDTO produto) {

        try {
            prep = conn.prepareStatement("INSERT INTO produtos VALUES(0,?,?,?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Cadastrado");

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, "Produto Não Cadastrado");

        }
    }

    public List<ProdutosDTO> listarProdutos() {
    String sql = "SELECT * FROM produtos";
    try {
        PreparedStatement st = conn.prepareStatement(sql);
        ResultSet rs = st.executeQuery();
        List<ProdutosDTO> listaProdutos = new ArrayList<>();
        while (rs.next()) {
            ProdutosDTO produto = new ProdutosDTO();
            produto.setId(rs.getInt("id"));
            produto.setNome(rs.getString("nome"));
            produto.setValor(rs.getInt("valor"));
            produto.setStatus(rs.getString("status"));
            listaProdutos.add(produto);
        }
        return listaProdutos;
    } catch (SQLException ex) {
        System.out.println("Erro ao conectar: " + ex.getMessage());
        return null;
    }
}

}
