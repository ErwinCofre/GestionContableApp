package gestioncontable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionContableApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú de Gestión Contable ---");
            System.out.println("1. Registrar una transacción");
            System.out.println("2. Mostrar transacciones");
            System.out.println("3. Cambiar estado de una transacción a TRUE");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1 -> registrarTransaccion(scanner);
                case 2 -> mostrarTransacciones();
                case 3 -> cambiarEstado(scanner);
                case 4 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 4);

        scanner.close();
    }

    private static void registrarTransaccion(Scanner scanner) {
        System.out.print("Ingrese la descripción de la transacción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese el monto de la transacción: ");
        double monto = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Ingrese el tipo (Ingreso/Egreso): ");
        String tipo = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO transacciones (descripcion, monto, tipo) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, descripcion);
            statement.setDouble(2, monto);
            statement.setString(3, tipo);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Transacción registrada con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar la transacción: " + e.getMessage());
        }
    }

    private static void mostrarTransacciones() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM transacciones";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n--- Transacciones Registradas ---");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String descripcion = resultSet.getString("descripcion");
                double monto = resultSet.getDouble("monto");
                String tipo = resultSet.getString("tipo");
                boolean estado = resultSet.getBoolean("estado");

                System.out.printf("ID: %d | Descripción: %s | Monto: %.2f | Tipo: %s | Estado: %s\n",
                        id, descripcion, monto, tipo, estado ? "TRUE" : "FALSE");
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar las transacciones: " + e.getMessage());
        }
    }

    private static void cambiarEstado(Scanner scanner) {

    }
}
