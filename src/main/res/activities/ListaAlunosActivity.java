package br.edu.unidavi.bsn.activities;

import java.util.List;
import com.example.listagemdealunos.R;
import br.edu.unidavi.bsn.dao.AlunoDAO;
import br.edu.unidavi.bsn.model.Aluno;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlunosActivity extends Activity {

    private ListView listaAlunos;

    protected Aluno alunoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        registerForContextMenu(listaAlunos);
        listaAlunos.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {

                Aluno alunoSelecionado = (Aluno) adapter.getItemAtPosition(position);
                Toast.makeText(ListaAlunosActivity.this,
                        "Clicou no intem: " + alunoSelecionado.getNome(), Toast.LENGTH_SHORT).show();

                Intent irParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                irParaFormulario.putExtra("alunoSelecionado", alunoSelecionado);

                startActivity(irParaFormulario);

            }
        });

        listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapter, View view,
                                           int posicao, long id) {

                alunoSelecionado = (Aluno) adapter.getItemAtPosition(posicao);
                // deletaAluno();
                // Toast.makeText(ListaAlunosActivity.this,"Nome clicado com toque mais longo: "+
                // alunoSelecionado.getId() +
                // alunoSelecionado.getNome(),Toast.LENGTH_LONG).show();

                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_novo:
                Toast.makeText(ListaAlunosActivity.this,
                        "Voce clicou no novoAluno", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(ListaAlunosActivity.this,
                        FormularioActivity.class);

                startActivity(intent);

                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {

        this.carregaLista();
        super.onResume();
    }

    private void carregaLista() {

        List<Aluno> alunos;
        AlunoDAO dao = new AlunoDAO(this);
        alunos = dao.getLista();
        dao.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,
                android.R.layout.simple_list_item_1, alunos);

        listaAlunos.setAdapter(adapter);

        // final String[] alunos = {"Anderson", "Filipe", "Guilherme"};
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        // android.R.layout.simple_list_item_1, alunos);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {

        menu.add("Ligar");
        menu.add("Enviar SMS");
        menu.add("Achar no Mapa");
        menu.add("Navegar no site");

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                // dao.deletar(alunoSelecionado);
                // dao.close();
                deletaAluno();
                Toast.makeText(
                                ListaAlunosActivity.this,
                                "Nome clicado com toque mais longo: "
                                        + alunoSelecionado.getNome(), Toast.LENGTH_LONG)
                        .show();
                carregaLista();
                return false;
            }
        });

        menu.add("Enviar E-mail");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void deletaAluno() {
        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
        dao.deletar(alunoSelecionado);
        dao.close();

        carregaLista();
    }
}

