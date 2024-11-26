package com.example.s4p2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static int orderNumber = 1830; // Número do pedido inicial

    private TextInputEditText inputCustomerName, inputQuantity;
    private RadioGroup radioGroupSize, radioGroupCrust;
    private Switch switchExtraCheese, switchExtraSauce;
    private ProgressBar progressBar;
    private RecyclerView recyclerViewMenu;
    private Button buttonSubmit, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando os componentes
        inputCustomerName = findViewById(R.id.inputCustomerName);
        inputQuantity = findViewById(R.id.inputQuantity);
        radioGroupSize = findViewById(R.id.radioGroupSize);
        radioGroupCrust = findViewById(R.id.radioGroupCrust);
        switchExtraCheese = findViewById(R.id.switchExtraCheese);
        switchExtraSauce = findViewById(R.id.switchExtraSauce);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewMenu = findViewById(R.id.recyclerViewMenu);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonCancel = findViewById(R.id.buttonCancel);

        // Configurando o RecyclerView
        setupRecyclerView();

        // Configurando os botões
        buttonSubmit.setOnClickListener(v -> submitOrder());
        buttonCancel.setOnClickListener(v -> cancelOrder());
    }

    private void setupRecyclerView() {
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<String> menuItems = new ArrayList<>();
        menuItems.add("Pizza de Calabresa");
        menuItems.add("Pizza de Mussarela");
        menuItems.add("Pizza de Frango com Catupiry");
        MenuAdapter adapter = new MenuAdapter(menuItems);
        recyclerViewMenu.setAdapter(adapter);
    }

    private void submitOrder() {
        // Mostrando ProgressBar
        progressBar.setVisibility(View.VISIBLE);

        // Capturando os dados do formulário
        String customerName = inputCustomerName.getText().toString().trim();
        String quantity = inputQuantity.getText().toString().trim();
        int selectedSizeId = radioGroupSize.getCheckedRadioButtonId();
        int selectedCrustId = radioGroupCrust.getCheckedRadioButtonId();

        if (customerName.isEmpty() || quantity.isEmpty() || selectedSizeId == -1 || selectedCrustId == -1) {
            Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return;
        }

        String size = ((RadioButton) findViewById(selectedSizeId)).getText().toString();
        String crust = ((RadioButton) findViewById(selectedCrustId)).getText().toString();
        boolean extraCheese = switchExtraCheese.isChecked();
        boolean extraSauce = switchExtraSauce.isChecked();

        // Incrementando o número do pedido
        orderNumber++;

        // Criando mensagem de resumo do pedido
        String orderSummary = "Número do Pedido: " + orderNumber + "\n"
                + "Nome do Cliente: " + customerName + "\n"
                + "Quantidade: " + quantity + "\n"
                + "Tamanho: " + size + "\n"
                + "Borda: " + crust + "\n"
                + "Queijo Extra: " + (extraCheese ? "Sim" : "Não") + "\n"
                + "Molho Extra: " + (extraSauce ? "Sim" : "Não");

        // Exibindo o AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Confirmação do Pedido")
                .setMessage(orderSummary)
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Pedido Confirmado! Obrigado!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> progressBar.setVisibility(View.GONE))
                .show();
    }

    private void cancelOrder() {
        inputCustomerName.setText("");
        inputQuantity.setText("");
        radioGroupSize.clearCheck();
        radioGroupCrust.clearCheck();
        switchExtraCheese.setChecked(false);
        switchExtraSauce.setChecked(false);
        Toast.makeText(this, "Pedido Cancelado!", Toast.LENGTH_SHORT).show();
    }
}
