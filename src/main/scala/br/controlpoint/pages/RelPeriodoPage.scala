package br.controlpoint.pages

import java.util.{ArrayList,List,Date}
import org.apache.wicket.extensions.markup.html.form.DateTextField
import org.apache.wicket.extensions.yui.calendar.DatePicker
import org.apache.wicket.markup.html.form.{ChoiceRenderer,Form,ListChoice}
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.spring.injection.annot.SpringBean
import br.controlpoint.entities.Usuario
import br.controlpoint.mediators.UsuarioMediator
import br.controlpoint.pages.base.PontoBasePage
import org.joda.time.DateTime

class RelPeriodoPage(usuario: Usuario, travarLista: java.lang.Boolean) extends PontoBasePage(usuario) {

  var usuarioSelecionado: Usuario = _

  def this(usuario: Usuario) = this(usuario, false)

  usuarioLogado = usuario

  var dataPesquisaInicio = new Date();
  var dataPesquisaFim = new Date();

  var form = new Form("form") {
    override protected def onSubmit() {
      setResponsePage(new PontoPage(usuarioLogado, usuarioSelecionado, new DateTime(dataPesquisaInicio), new DateTime(dataPesquisaFim), false));
    }
  };
  add(form);

  var dateTextFieldInicio = new DateTextField("dataPesquisaInicio", new PropertyModel[Date](this, "dataPesquisaInicio"), "dd/MM/yy");
  dateTextFieldInicio.add(new DatePicker());
  dateTextFieldInicio.setRequired(true);
  form.add(dateTextFieldInicio);

  var dateTextFieldFim = new DateTextField("dataPesquisaFim", new PropertyModel[Date](this, "dataPesquisaFim"), "dd/MM/yy");
  dateTextFieldFim.add(new DatePicker());
  dateTextFieldFim.setRequired(true);
  form.add(dateTextFieldFim);

  var listaUsuario = new ArrayList[Usuario]();

  if (usuarioLogado.adm.asInstanceOf[Boolean]) {
    listaUsuario.addAll(usuarioMediator.listaUsuarios);
  } else {
    listaUsuario.add(usuarioLogado);
  }

  var listChoice = new ListChoice[Usuario]("listaUsuario", new PropertyModel[Usuario](this, "usuarioSelecionado"), listaUsuario, new ChoiceRenderer[Usuario]("nome"), 1);
  listChoice.setRequired(true);
  if (travarLista.asInstanceOf[Boolean]) {
    usuarioSelecionado = usuarioLogado;
    listChoice.setVisible(false);
  }
  form.add(listChoice);

}