package br.controlpoint.pages

import br.controlpoint.entities.Usuario
import java.util.{ Date, ArrayList }
import org.apache.wicket.extensions.markup.html.form.DateTextField
import org.apache.wicket.extensions.yui.calendar.DatePicker
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.form.{ ListChoice, Form, ChoiceRenderer }
import org.apache.wicket.model.PropertyModel
import org.joda.time.DateTime
import br.controlpoint.pages.base.PontoBasePage

class RelDiaPage(usuario: Usuario) extends PontoBasePage(usuario) {

  var dataPesquisa: Date = _

  var usuarioSelecionado: Usuario = _

  var container: WebMarkupContainer = _

  dataPesquisa = new Date();
  container = new WebMarkupContainer("containerPanel");
  container.setOutputMarkupId(true);
  add(container);

  var form = new Form("form") {
    override protected def onSubmit() {
      setResponsePage(new PontoPage(usuarioLogado, usuarioSelecionado, new DateTime(dataPesquisa), new DateTime(dataPesquisa), false));
    }
  };
  add(form);

  var dateTextField = new DateTextField("dataPesquisa", new PropertyModel[Date](this, "dataPesquisa"), "dd/MM/yy");
  dateTextField.add(new DatePicker());
  dateTextField.setRequired(true);
  form.add(dateTextField);

  var listaUsuario = new ArrayList[Usuario]()

  if (usuarioLogado.adm.asInstanceOf[Boolean]) {
    listaUsuario.addAll(usuarioMediator.listaUsuarios)
  } else {
    listaUsuario.add(usuarioLogado)
  }

  var listChoice = new ListChoice[Usuario]("listaUsuario", new PropertyModel[Usuario](this, "usuarioSelecionado"), listaUsuario, new ChoiceRenderer[Usuario]("nome"), 1)
  listChoice.setRequired(true);
  form.add(listChoice);

}