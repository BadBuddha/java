public class Paramteres{
    public void Parmaters(String[]){

    }

    public static  Options parseOptions(String[] args){
    EnumSet<CommandOptions> options = EnumSet.noneOf(CommandOptions.class);
    // gather opts until we hit the first command.
    int pos = 0;
    for (String opt:args){
        if (opt.startsWith("-")){
            boolean found = false;
            CommandOptions[] allOpts = CommandOptions.values();
            for (CommandOptions anOption:allOpts){
                if (opt.equals("-" + anOption.getOpt())){
                    options.add(anOption);
                    found = true;
                    break;
                }
            }
            if (!found){
                System.out.println("Option not support [" + opt + "]");
            }
        } else {
            break;
        }
        pos++;
    }
    if (options.contains(CommandOptions.VERBOSE)){
        System.out.println("Current options [" + options + "]");
    }
    return new Options(com.thalesifec.framework.tool.utils.Arrays.copyOfRange(args, pos, args.length), options);
}


public static void main(String[] args){

}









}
