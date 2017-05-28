package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.FuncaoColaborador;

public class FuncaoColaboradorDAO {
	public int criar(FuncaoColaborador colaborador) {
		String sqlInsert = "INSERT INTO colaborador(login,nome, cpf, sexo, tel, email, senha) VALUES (?,?,?,?, ?,?, ?)";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
			stm.setString(1, colaborador.getLogin());
			stm.setString(2, colaborador.getNome());
			stm.setString(3, colaborador.getCpf());
			stm.setString(4, colaborador.getSexo());
			stm.setString(5, colaborador.getTel());
			stm.setString(6, colaborador.getEmail());
			stm.setString(7, colaborador.getSenha());
			stm.execute();
			String sqlQuery = "SELECT LAST_INSERT_ID()";
			try (PreparedStatement stm2 = conn.prepareStatement(sqlQuery);
					ResultSet rs = stm2.executeQuery();) {
				if (rs.next()) {
					colaborador.setIdcolab(rs.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colaborador.getIdcolab();
	}

	public void atualizar(FuncaoColaborador colaborador) {
		String sqlUpdate = "UPDATE colaborador SET login=?,nome=?,cpf=?,sexo=?, tel=?, email=?, senha=? WHERE idcolab=?";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
			stm.setString(1, colaborador.getLogin());
			stm.setString(2, colaborador.getNome());
			stm.setString(3, colaborador.getCpf());
			stm.setString(4, colaborador.getSexo());
			stm.setString(5, colaborador.getTel());
			stm.setString(6, colaborador.getEmail());
			stm.setString(7, colaborador.getSenha());
			stm.setInt(8, colaborador.getIdcolab());
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void excluir(int idcolab) {
		String sqlDelete = "DELETE FROM colaborador WHERE idcolab = ?";
		String sqlDelete2 = "DELETE FROM sugestao WHERE  colaborador_idcolab = ?";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm2 = conn.prepareStatement(sqlDelete2);) {
			stm2.setInt(1, idcolab);
			stm2.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
			stm.setInt(1, idcolab);
			stm.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FuncaoColaborador carregar(int idcolab) {
		FuncaoColaborador colaborador = new FuncaoColaborador();
		colaborador.setIdcolab(idcolab);
		String sqlSelect = "SELECT login,nome,cpf, sexo, tel, email, senha FROM colaborador WHERE colaborador.idcolab = ?";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setInt(1, colaborador.getIdcolab());
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					colaborador.setLogin(rs.getString("login"));
					colaborador.setNome(rs.getString("nome"));
					colaborador.setCpf(rs.getString("cpf"));
					colaborador.setSexo(rs.getString("sexo"));
					colaborador.setTel(rs.getString("tel"));
					colaborador.setEmail(rs.getString("email"));
					colaborador.setSenha(rs.getString("senha"));
				} else {
					colaborador.setIdcolab(-1);
					colaborador.setLogin(null);
					colaborador.setNome(null);
					colaborador.setCpf(null);
					colaborador.setSexo(null);
					colaborador.setTel(null);
					colaborador.setEmail(null);
					colaborador.setSenha(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return colaborador;
	}
	
	  public void recuperar(FuncaoColaborador colaborador) {
	      String sqlUpdate = "UPDATE colaborador SET senha = ? WHERE idcolab = ?";
	   
	      try (Connection conn = ConnectionFactory.obtemConexao();
	    		  PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
	         stm.setString(1,colaborador.getSenha());
	         stm.setInt(2, colaborador.getIdcolab());
	         stm.executeUpdate();
	   	} catch (Exception e) {
	  		e.printStackTrace();
	  	}
	  }
	  
	  
	

	public ArrayList<FuncaoColaborador> listarColaboradores() {
		FuncaoColaborador colaborador;
		ArrayList<FuncaoColaborador> lista = new ArrayList<>();
		String sqlSelect = "SELECT idcolab, login, nome, cpf, sexo, tel, email, senha FROM colaborador";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					colaborador = new FuncaoColaborador();
					colaborador.setIdcolab(rs.getInt("idcolab"));
					colaborador.setLogin(rs.getString("login"));
					colaborador.setNome(rs.getString("nome"));
					colaborador.setCpf(rs.getString("cpf"));
					colaborador.setSexo(rs.getString("sexo"));
					colaborador.setTel(rs.getString("tel"));
					colaborador.setEmail(rs.getString("email"));
					colaborador.setSenha(rs.getString("senha"));
					lista.add(colaborador);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return lista;
	}

	public ArrayList<FuncaoColaborador> listarColaboradores(String chave) {
		FuncaoColaborador colaborador;
		ArrayList<FuncaoColaborador> lista = new ArrayList<>();
		String sqlSelect = "SELECT idcolab, login, nome, cpf,sexo, tel, email, senha FROM colaborador where upper(nome) like ?";
		// usando o try with resources do Java 7, que fecha o que abriu
		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setString(1, "%" + chave.toUpperCase() + "%");
			try (ResultSet rs = stm.executeQuery();) {
				while (rs.next()) {
					colaborador = new FuncaoColaborador();
					colaborador.setIdcolab(rs.getInt("idcolab"));
					colaborador.setLogin(rs.getString("login"));
					colaborador.setNome(rs.getString("nome"));
					colaborador.setCpf(rs.getString("cpf"));
					colaborador.setSexo(rs.getString("sexo"));
					colaborador.setTel(rs.getString("tel"));
					colaborador.setEmail(rs.getString("email"));
					colaborador.setSenha(rs.getString("senha"));
					lista.add(colaborador);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
		}
		return lista;
	}
	
	public boolean validar(String login, String senha) {

		String sqlSelect = "SELECT * FROM colaborador WHERE login = ? AND senha = ?";

		try (Connection conn = ConnectionFactory.obtemConexao();
				PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
			stm.setString(1, login);
			stm.setString(2, senha);
			try (ResultSet rs = stm.executeQuery();) {
				if (rs.next()) {
					return true;
				} else {
					return false;
		}
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} catch (SQLException e1) {
			System.out.print(e1.getStackTrace());
			return false;
		}
		
	}

}

	
	
