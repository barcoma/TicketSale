package va.a6.ticketservice;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketSaleDBHandler {
    private DataSource dataSource;

    TicketSaleDBHandler() {
        ServletContext context = MyServletContextListener.ctx;
        dataSource = (DataSource) context.getAttribute("dataSource");
    }

    public void updateTicketTable(Ticket ticket) {
        String sql = "UPDATE ticket SET state = ?, owner = ? WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, ticket.getState().toString());
                preparedStatement.setString(2, ticket.getTicketOwner());
                preparedStatement.setInt(3, ticket.getId());
                preparedStatement.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public TicketState getTicketFromDB(int id) {
        String state = "";
        String sql = "SELECT state FROM ticket WHERE id=?;";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                state = rs.getString("state");
            }
            return TicketState.valueOf(state);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void updateOptionsTable(boolean areReservationPossible) {
        String sql = "UPDATE options SET reservationsPossible";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, areReservationPossible ? 1 : 0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }


}
