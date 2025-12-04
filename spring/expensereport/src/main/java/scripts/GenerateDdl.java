package scripts;

import com.revature.expensereport.model.Expense;
import com.revature.expensereport.model.Report;
import com.revature.expensereport.model.User;
import jakarta.persistence.Entity;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.util.EnumSet;

public class GenerateDdl {
    static void main(String[] args) {
        Logging.disableLogging();

        var registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                .build();

        var reflections = new Reflections("com.revature.expensereport");
        var classes = reflections.getTypesAnnotatedWith(Entity.class);
        var asd = reflections.getAll(Scanners.TypesAnnotated);

        System.out.println("asd: " + asd.size());
        asd.forEach(System.out::println);
        System.out.println("classes found: " + classes.size());

        var sources = new MetadataSources(registry);
        // classes.forEach(sources::addAnnotatedClass);
        sources.addAnnotatedClass(Report.class).addAnnotatedClass(Expense.class).addAnnotatedClass(User.class);

        var metadata = sources.buildMetadata();

        var schemaExport = new SchemaExport();
        schemaExport.setOutputFile(args[0]);
        schemaExport.setFormat(true);
        schemaExport.setDelimiter(";");
        schemaExport.execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.CREATE, metadata);
    }
}
