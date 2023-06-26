package br.edu.unidavi.bsn.helpers;

import com.example.listagemdealunos.R;
import com.example.listagemdealunos.R.id;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.edu.unidavi.bsn.activities.FormularioActivity;
import br.edu.unidavi.bsn.model.Aluno;

public class FormularioHelper {
    private EditText nome;
    private EditText telefone;
    private EditText site;
    private SeekBar nota;
    private EditText endereco;
    private ImageView botaoImagem;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {

        nome = (EditText) activity.findViewById(R.id.nome);
        telefone = (EditText) activity.findViewById(R.id.telefone);
        site = (EditText) activity.findViewById(R.id.site);
        nota = (SeekBar) activity.findViewById(R.id.nota);
        endereco = (EditText) activity.findViewById(R.id.endereco);
        botaoImagem = (ImageView) activity.findViewById(R.id.foto);
        aluno = new Aluno();
    }

    public Aluno pegaAlunoDoFormulario() {

        aluno.setNome(nome.getEditableText().toString());
        aluno.setEndereco(endereco.getEditableText().toString());
        aluno.setSite(site.getEditableText().toString());
        aluno.setTelefone(telefone.getEditableText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));

        return aluno;
    }

    public void colocaAlunoNoFormulario(Aluno alunoParaSerAlterado) {
        nome.setText(alunoParaSerAlterado.getNome());
        telefone.setText(alunoParaSerAlterado.getTelefone());
        site.setText(alunoParaSerAlterado.getSite());
        endereco.setText(alunoParaSerAlterado.getEndereco());
        nota.setProgress((int) alunoParaSerAlterado.getNota());
    }
}
