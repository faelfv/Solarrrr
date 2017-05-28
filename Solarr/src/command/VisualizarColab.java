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

public class VisualizarColab implements Command {

	@Override
	public void executar(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pIdcolab = request.getParameter("idcolab");
		String pNome = request.getParameter("nome");
		String pCpf = request.getParameter("cpf");
		String pSexo = request.getParameter("sexo");
		String pTel = request.getParameter("tel");
		String pEmail = request.getParameter("email");
		String pLogin = request.getParameter("login");
        String pSenha = request.getParameter("senha");
        int idcolab = -1;
		try {
			idcolab = Integer.parseInt(pIdcolab);
		} catch (NumberFormatException e) {

		}

		FuncaoColaborador colaborador = new FuncaoColaborador();
		 	colaborador.setIdcolab(idcolab);
		 	colaborador.setNome(pNome);
			colaborador.setCpf(pCpf);
			colaborador.setSexo(pSexo);
			colaborador.setTel(pTel);
			colaborador.setEmail(pEmail);
			colaborador.setLogin(pLogin);
	        colaborador.setSenha(pSenha);
		FuncaoColaboradorService cs = new FuncaoColaboradorService();
		RequestDispatcher view = null;

		colaborador = cs.carregar(colaborador.getIdcolab());
		request.setAttribute("colaborador", colaborador);
		view = request.getRequestDispatcher("VisualizarColaborador.jsp");

		view.forward(request, response);

	}

}
