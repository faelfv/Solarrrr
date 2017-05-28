package command;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.FuncaoColaborador;
import service.FuncaoColaboradorService;

public class ListarColabBuscar implements Command {
	@Override
	public void executar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String chave = request.getParameter("data[search]");
		FuncaoColaboradorService fcs = new FuncaoColaboradorService();
		ArrayList<FuncaoColaborador> lista = null;
		HttpSession session = request.getSession();
		if (chave != null && chave.length() > 0) {
			lista = fcs.listarColaboradores(chave);
		} else {
			lista = fcs.listarColaboradores();
		}
		session.setAttribute("lista", lista);

		RequestDispatcher dispatcher = request
				.getRequestDispatcher("ListarColaboradores.jsp");
		dispatcher.forward(request, response);
	}
}
