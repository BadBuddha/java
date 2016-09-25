public class Main(){
    public static final Main MAIN = new Main();


    //

    // public void Main(){
    //
    // }


    //

    public void execute(String[] args) {
        if (!parse(args)) {
            return;
        }
        String cmd = commander.getParsedCommand();
        AbstractCommand command = commands.get(cmd);
        try {
            if (command == null) {
                commander.usage();
                return;
            }
            command.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //

    public static void main(String[] args) {
        MAIN.execute(args);
    }

}
