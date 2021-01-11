package cliente.gui;

import extraPackage.InAndOut;
import flujodetrabajo.*;
import extraPackage.TextPrompt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TableroGUI extends JDialog {
    private FlujoDeTrabajo flujoDeTrabajo;
    private DefaultListModel[] modeloL = new DefaultListModel[5];

    //Paneles
    private JPanel contentPane;
    private JPanel panelPrincipal;
    private JPanel panelBotones;
    private JPanel panelSecundario;
    private JPanel Lists;

    //Botones
    private JButton buttonOK;
    private JButton buttonCancel;
    private JButton buttonAgregarActividad;
    private JButton buttonAgregarTarea;
    private JButton buttonDelete;

    //Campos de texto
    private JTextField textFieldActividad;
    private JTextField textFieldTarea;

    //Combobox
    private JComboBox comboBoxFase;
    private JComboBox comboBoxActividad;
    private JComboBox comboBoxTarea;

    //Listas
    private JList listIdeas;
    private JList listToDo;
    private JList listDoing;
    private JList listTest;
    private JList listDone;

    public TableroGUI() {
        //Inicializacion con lo que ya existe
        flujoDeTrabajo = InAndOut.deserialize();
        if (flujoDeTrabajo==null) {
            String [] str = {"Ideas", "To Do", "Doing", "Test", "Done"};
            flujoDeTrabajo = new FlujoDeTrabajo("Mi flujo de trabajo");
            for(int i=0; i<5; i++)
            {
                Fase fase = new Fase(str[i], flujoDeTrabajo);
                flujoDeTrabajo.getFases().add(fase);
            }
        }
        actualizarTablero();

        //Algunos detalles sobre ventanas
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //placeHolders for TextFields
        TextPrompt placeHAct = new TextPrompt("Actividad...", textFieldActividad);
        TextPrompt placeHTarea = new TextPrompt("Tarea...", textFieldTarea);
        placeHAct.changeAlpha(0.75f);
        placeHAct.changeStyle(Font.ITALIC);
        placeHTarea.changeAlpha(0.75f);
        placeHTarea.changeStyle(Font.ITALIC);

        //Botones para salir
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        /* Ocultar del botón delete
        buttonDelete.setVisible(false);
        comboBoxTarea.setVisible(false);*/

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        buttonAgregarActividad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!textFieldActividad.getText().equals(""))
                {
                    Actividad actividad = new Actividad(textFieldActividad.getText(), flujoDeTrabajo);
                    flujoDeTrabajo.getActividades().add(actividad);
                    actualizarTablero();
                } else
                {
                    JOptionPane.showMessageDialog(null, "Favor de asignar nombre a la actividad", "Actividad sin nombre", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        buttonAgregarTarea.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!textFieldTarea.getText().equals(""))
                {
                    Fase fase = flujoDeTrabajo.getFases().get(comboBoxFase.getSelectedIndex());
                    Actividad actividad = flujoDeTrabajo.getActividades().get(comboBoxActividad.getSelectedIndex());

                    Tarea tarea = new Tarea(textFieldTarea.getText(), actividad, fase, flujoDeTrabajo);
                    flujoDeTrabajo.getTareas().add(tarea);
                    actividad.getTareas().add(tarea);
                    fase.getTareas().add(tarea);
                    actualizarTablero();
                } else
                {
                    JOptionPane.showMessageDialog(null, "Favor de asignar nombre a la tarea", "Tarea sin nombre", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //comboBoxTarea.removeItemAt(comboBoxTarea.getSelectedIndex());

                Fase fase = flujoDeTrabajo.getTareas().get(comboBoxTarea.getSelectedIndex()).getFase();
                Actividad actividad = flujoDeTrabajo.getTareas().get(comboBoxTarea.getSelectedIndex()).getActividad();
                Tarea tarea = flujoDeTrabajo.getTareas().get(comboBoxTarea.getSelectedIndex());

                flujoDeTrabajo.getTareas().removeElement(tarea);
                fase.getTareas().removeElement(tarea);
                actividad.getTareas().remove(tarea);
                actualizarTablero();
            }
        });
    }

    private void onOK() {
        InAndOut.serialize(flujoDeTrabajo);
        dispose();
    }

    private void onCancel() {
        //Namas pa acabar el codigo sin guardar nada asi es
        dispose();
    }

    private void actualizarTablero(){
        comboBoxFase.removeAllItems();
        for (int i = 0; i < flujoDeTrabajo.getFases().size(); i++) {
            comboBoxFase.addItem(flujoDeTrabajo.getFases().get(i).getNombre());
        }

        comboBoxActividad.removeAllItems();
        for (int j = 0; j < flujoDeTrabajo.getActividades().size(); j++) {
            comboBoxActividad.addItem(flujoDeTrabajo.getActividades().get(j).getNombre());
        }

        comboBoxTarea.removeAllItems();
        for (int j = 0; j < flujoDeTrabajo.getTareas().size(); j++) {
            comboBoxTarea.addItem(flujoDeTrabajo.getTareas().get(j).getNombre());
        }

        for(int k = 0; k < 5; k++)
        {
            modeloL[k] = new DefaultListModel();
        }

        listIdeas.setModel(modeloL[0]);
        listToDo.setModel(modeloL[1]);
        listDoing.setModel(modeloL[2]);
        listTest.setModel(modeloL[3]);
        listDone.setModel(modeloL[4]);

        for(int i = 0; i < 5; i++) {
            for (int j = 0; j < flujoDeTrabajo.getFases().get(i).getTareas().size(); j++){
                modeloL[i].addElement(flujoDeTrabajo.getFases().get(i).getTareas().get(j));
            }
        }
    }

    public static void main(String[] args) {
        TableroGUI dialog = new TableroGUI();
        dialog.pack();
        dialog.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
        dialog.setTitle("Tablero Kanban - 0.2.1");
        dialog.setSize(1280, 720);
        dialog.setResizable(false);
        try{
            dialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dialog.setVisible(true);
        System.exit(0);
    }

}
