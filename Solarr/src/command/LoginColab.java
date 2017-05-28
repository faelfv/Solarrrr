package command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.FuncaoColaborador;
import service.FuncaoColaboradorService;

public class LoginColab implements Command {
	@Override
	public void executar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		FuncaoColaborador usuario = new FuncaoColaborador();
		usuario.setLogin(login);
		usuario.setSenha(senha);
		FuncaoColaboradorService service = new FuncaoColaboradorService();
		if (service.validar(usuario)) {
			HttpSession session = request.getSession();
			session.setAttribute("logado", usuario);
			System.out.println("Logou " + usuario);
		} else {
			System.out.println("Não Logou " + usuario);
			throw new ServletException("Login/Senha inválidos");
		}
		response.sendRedirect("index.jsp");
	}
}