package va.a6.ticketservice;

import javax.servlet.ServletContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUpdate {
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;

    DatabaseUpdate(){
        ServletContext context = MyServletContextListener.ctx;
        dataSource = (DataSource) context.getAttribute("dataSource");
    }

    public void updateDB(Ticket ticket){
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE ticket SET state = ?, owner = ? WHERE id=?"
            );
            preparedStatement.setString(1, ticket.getState().toString());
            preparedStatement.setString(2, ticket.getTicketOwner());
            preparedStatement.setInt(3, ticket.getId());
            preparedStatement.execute();
        } catch (SQLException e){
            throw new RuntimeException();
        }
        finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

}
