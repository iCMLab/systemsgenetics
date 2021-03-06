package nl.umcg.westrah.binarymetaanalyzer;

import nl.umcg.westrah.binarymetaanalyzer.westrah.binarymetaanalyzer.posthoc.*;
import nl.umcg.westrah.binarymetaanalyzer.westrah.binarymetaanalyzer.posthoc.ld.BinaryFileLD;
import nl.umcg.westrah.binarymetaanalyzer.westrah.binarymetaanalyzer.posthoc.ld.BinaryFileSorter;
import org.apache.commons.cli.*;

import java.io.IOException;

public class BinaryMetaAnalysisCLI {

    private static final Options OPTIONS;

    static {
        OPTIONS = new Options();

        Option option = Option.builder()
                .longOpt("meta")
                .desc("Run meta-analysis")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("applyfdr")
                .desc("Apply the FDR values of one file on another")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("replicationtable")
                .desc("Make a table for easy comparison of multiple qtl files")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("replicationtablegtex")
                .desc("Make a table for easy comparison of multiple qtl files using a custom pvaluethreshold")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("bhfdr")
                .desc("Calculate Benjamini Hochberg FDR on an eQTL file")
                .build();
        OPTIONS.addOption(option);


        option = Option.builder()
                .longOpt("convertsummarystats")
                .desc("Concatenate summary stats over all datasets, per snp and gene")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("sortbinaryfile")
                .desc("Sort binary cis-eQTL file given a set of snp/gene combos")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("binaryfileld")
                .desc("Calculate LD over files created with --sortbinaryfile")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("internalmeta")
                .desc("Run internal meta-analysis")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("calculateeqtlvariance")
                .desc("Calculate variance for each eQTL in an eQTL file")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("createsettingsinternalmeta")
                .desc("Create settings files for InternalMetaAnalyzer")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("createsettingsbinarymeta")
                .desc("SettingsCreator: create settings file for BinaryMetaAnalyzer")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("comparepermute")
                .desc("Compare QTL permutation datasets")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("zmeanandvar")
                .desc("Determine Z-score mean and variance per gene/probe and per permutation")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("zscorematcheck")
                .desc("Determine Z-score mean and variance per gene/probe and per permutation, from z-score matrices")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("leaveoneout")
                .desc("Leave one out meta-analysis")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder("s")
                .longOpt("settings")
                .hasArg()
                .desc("XML settings file")
                .build();
        OPTIONS.addOption(option);
        option = Option.builder("rt")
                .longOpt("replacetext")
                .hasArg()
                .desc("Replace a pattern")
                .build();
        OPTIONS.addOption(option);
        option = Option.builder("rtw")
                .longOpt("replacetextwith")
                .hasArg()
                .desc("Replace the pattern with this text")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder("e")
                .longOpt("eqtls")
                .hasArg()
                .desc("eQTL Files (comma separated)")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder("g")
                .longOpt("groups")
                .hasArg()
                .desc("File with group definition, tab-separated, format: cohort\tgroup")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder("o")
                .longOpt("out")
                .hasArg()
                .desc("Output location (for QC and settings creator)")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder("a")
                .longOpt("annot")
                .hasArg()
                .desc("SNP annotation file (SNP ID should be on first column, tab separated)")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder("t")
                .longOpt("threshold")
                .hasArg()
                .desc("Threshold for p-val")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("nonan")
                .desc("Force presence of QTL in all datasets")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("in")
                .hasArg()
                .desc("SettingsCreator: input dataset definition file")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("in2")
                .hasArg()
                .desc("Second input")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("averagingmethod")
                .hasArg()
                .desc("SettingsCreator: Averaging method for internal meta")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("nrperm")
                .hasArg()
                .desc("SettingsCreator: Nr Permutations to use")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("scriptlocserver")
                .hasArg()
                .desc("SettingsCreator: settings location on server")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("scriptloclocal")
                .hasArg()
                .desc("SettingsCreator: settings location on local machine")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("tool")
                .hasArg()
                .desc("SettingsCreator: location of tool on server")
                .build();
        OPTIONS.addOption(option);


        option = Option.builder()
                .longOpt("threads")
                .hasArg()
                .desc("SettingsCreator: number of threads to set")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("snpannotation")
                .hasArg()
                .desc("SettingsCreator: location of snp annotation on server")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("probeconfine")
                .hasArg()
                .desc("SettingsCreator: location of probe confinement file on server")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("snpprobeconfine")
                .hasArg()
                .desc("SettingsCreator: location of snpprobe confinement file on server")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("snpconfine")
                .hasArg()
                .desc("SettingsCreator: location of snp confinement file on server")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("geneannotation")
                .hasArg()
                .desc("SettingsCreator: location of gene annotation on server")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("nreqtls")
                .hasArg()
                .desc("SettingsCreator: nr of eqtls to output")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("minnrofcohorts")
                .hasArg()
                .desc("Minimum number of cohorts (for QTL filter/ld calc)")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("minnrofsamples")
                .hasArg()
                .desc("Minimum number of samples (for ld calc)")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("ldmethod")
                .hasArg()
                .desc("Method for LD calc (pearson|weightedpearson|innerproduct|weightedinnerproduct)")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("usetmp")
                .desc("Use tmp dir for temporary storage")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("filtereqtl")
                .desc("Use tmp dir for temporary storage")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("onlyoutputsignificant")
                .desc("Only output signifciant results when applying FDR")
                .build();
        OPTIONS.addOption(option);


        option = Option.builder()
                .longOpt("cismatrixconvert")
                .desc("Convert FHS cis data to BinaryMatrix")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("replicationdeterminalambda")
                .desc("Determine lambda inflation for folder with eQTL results")
                .build();
        OPTIONS.addOption(option);

        option = Option.builder()
                .longOpt("names")
                .hasArg()
                .desc("Dataset names")
                .build();
        OPTIONS.addOption(option);


    }

    public static void main(String[] args) {
        BinaryMetaAnalysisCLI cli = new BinaryMetaAnalysisCLI(args);
    }

    public BinaryMetaAnalysisCLI(String[] args) {
        boolean run = true;
        try {
            CommandLineParser parser = new DefaultParser();
            final CommandLine cmd = parser.parse(OPTIONS, args, false);

            if (cmd.hasOption("calculateeqtlvariance")) {

                String in = "";
                String out = "";
                if (!cmd.hasOption("in") || !cmd.hasOption("out")) {
                    System.out.println("Options for calculateeqtlvariance:");
                    System.out.println("--in");
                    System.out.println("--out");
                } else {

                    in = cmd.getOptionValue("in");
                    out = cmd.getOptionValue("out");

                    EQTLFileR2Calculator e = new EQTLFileR2Calculator();
                    e.run(in, out);
                }

            } else if (cmd.hasOption("replicationdeterminalambda")) {
                if (!cmd.hasOption("in") || !cmd.hasOption("out") || !cmd.hasOption("names") || !cmd.hasOption("nrperm")) {
                    System.out.println("Options for replicationdeterminalambda:");
                    System.out.println("--in");
                    System.out.println("--in2");
                    System.out.println("--out");
                    System.out.println("--nrperm");
                    System.out.println("--names");

                } else {
                    QTLReplicationTable t = new QTLReplicationTable();
                    String in = cmd.getOptionValue("in");
                    String in2 = cmd.getOptionValue("in2");
                    String out = cmd.getOptionValue("out");
                    String names = cmd.getOptionValue("names");
                    Integer nrperm = Integer.parseInt(cmd.getOptionValue("nrperm"));
                    t.calculateLambdaForPerm(in, in2, names, nrperm, out);
                }
            } else if (cmd.hasOption("replicationtable")) {

                if (!cmd.hasOption("in") || !cmd.hasOption("in2") || !cmd.hasOption("names") || !cmd.hasOption("out")) {
                    System.out.println("Options for replicationtable:");
                    System.out.println("--in");
                    System.out.println("--out");
                    System.out.println("--in2");
                    System.out.println("--names");
                    System.out.println("[--onlyoutputsignificant]");
                    System.out.println("[--threshold]");
                    System.out.println("[--minnrofcohorts]");

                } else {
                    QTLReplicationTable qt = new QTLReplicationTable();
                    String ref = cmd.getOptionValue("in");
                    String other = cmd.getOptionValue("in2");
                    String othernames = cmd.getOptionValue("names");
                    String outputloc = cmd.getOptionValue("out");
                    boolean includenonsignificant = true;
                    if (cmd.hasOption("onlyoutputsignificant")) {
                        includenonsignificant = false;
                    }
                    double fdrthreshold = 0.05;
                    if (cmd.hasOption("threshold")) {
                        String fdrthresholdstr = cmd.getOptionValue("threshold");
                        fdrthreshold = Double.parseDouble(fdrthresholdstr);
                    }

                    int minnroverlap = 0;
                    if (cmd.hasOption("minnrofcohorts")) {
                        String minoverlapstr = cmd.getOptionValue("minnrofcohorts");
                        minnroverlap = Integer.parseInt(minoverlapstr);
                    }

                    qt.run(ref, other, othernames, outputloc, includenonsignificant, minnroverlap, fdrthreshold);
                }


            } else if (cmd.hasOption("replicationtablegtex")) {

                if (!cmd.hasOption("in") || !cmd.hasOption("in2") || !cmd.hasOption("names") || !cmd.hasOption("out")) {
                    System.out.println("Options for replicationtable:");
                    System.out.println("--in");
                    System.out.println("--out");
                    System.out.println("--in2");
                    System.out.println("--names");
                    System.out.println("[--onlyoutputsignificant]");
                    System.out.println("[--threshold]");
                    System.out.println("[--minnrofcohorts]");

                } else {
                    QTLReplicationTable qt = new QTLReplicationTable();
                    String ref = cmd.getOptionValue("in");
                    String other = cmd.getOptionValue("in2");
                    String othernames = cmd.getOptionValue("names");
                    String outputloc = cmd.getOptionValue("out");
                    boolean includenonsignificant = true;
                    if (cmd.hasOption("onlyoutputsignificant")) {
                        includenonsignificant = false;
                    }
                    double fdrthreshold = 0.05;
                    if (cmd.hasOption("threshold")) {
                        String fdrthresholdstr = cmd.getOptionValue("threshold");
                        fdrthreshold = Double.parseDouble(fdrthresholdstr);
                    }

                    int minnroverlap = 0;
                    if (cmd.hasOption("minnrofcohorts")) {
                        String minoverlapstr = cmd.getOptionValue("minnrofcohorts");
                        minnroverlap = Integer.parseInt(minoverlapstr);
                    }

                    qt.rungtex(ref, other, othernames, outputloc, includenonsignificant, minnroverlap, fdrthreshold);
                }


            } else if (cmd.hasOption("bhfdr")) {
                BHFDR f = new BHFDR();
                String in = cmd.getOptionValue("in");
                double threshold = 0.05;
                if (cmd.hasOption("threshold")) {
                    threshold = Double.parseDouble(cmd.getOptionValue("threshold"));
                }
                String out = cmd.getOptionValue("out");
                f.run(in, threshold, out);
            } else if (cmd.hasOption("cismatrixconvert")) {
                MatrixConverterCis c = new MatrixConverterCis();
                String in = cmd.getOptionValue("in");
                String in2 = cmd.getOptionValue("in2");
                String out = cmd.getOptionValue("out");
                c.run(in, in2, out);
            } else if (cmd.hasOption("comparepermute")) {

                CompareSNPProbeCombosBetweenFiles d = new CompareSNPProbeCombosBetweenFiles();
                d.run(cmd.getOptionValue("in"), cmd.getOptionValue("in2"), cmd.getOptionValue("out"));

            } else if (cmd.hasOption("meta")) {
                String settings = null;
                String texttoreplace = null;
                String replacewith = null;
                if (cmd.hasOption("settings")) {
                    settings = cmd.getOptionValue("settings");
                }
                if (cmd.hasOption("rt")) {
                    texttoreplace = cmd.getOptionValue("rt");
                }
                if (cmd.hasOption("rtw")) {
                    replacewith = cmd.getOptionValue("rtw");
                }

                boolean usetmp = false;
                if (cmd.hasOption("usetmp")) {
                    usetmp = true;
                }

                if (settings == null) {
                    System.err.println("Error: Please provide settings with --meta");
                    System.out.println();
                    printHelp();
                } else {
                    BinaryMetaAnalysis bm = new BinaryMetaAnalysis(settings, texttoreplace, replacewith, usetmp);
                    bm.run();
                }
            } else if (cmd.hasOption("sortbinaryfile")) {
                String settings = null;
                String texttoreplace = null;
                String replacewith = null;
                if (cmd.hasOption("settings")) {
                    settings = cmd.getOptionValue("settings");
                }
                if (cmd.hasOption("rt")) {
                    texttoreplace = cmd.getOptionValue("rt");
                }
                if (cmd.hasOption("rtw")) {
                    replacewith = cmd.getOptionValue("rtw");
                }

                boolean usetmp = false;
                if (cmd.hasOption("usetmp")) {
                    usetmp = true;
                }

                if (settings == null) {
                    System.err.println("Error: Please provide settings with --meta");
                    System.out.println();
                    printHelp();
                } else {
                    BinaryFileSorter bm = new BinaryFileSorter(settings, texttoreplace, replacewith, usetmp);
                    bm.run();
                }
            } else if (cmd.hasOption("binaryfileld")) {

                if (!cmd.hasOption("in") || !cmd.hasOption("out") || !cmd.hasOption("geneannotation") || !cmd.hasOption("threads")) {

                    System.out.println("Usage: --binaryfileld");
                    System.out.println("--in\tindir");
                    System.out.println("--out\toutdir");
                    System.out.println("--geneannotation\tlist of genes");
                    System.out.println("--threads\tnr threads to use");
                } else {

                    int minnrsamples = 0;
                    int minnrvalues = 0;
                    boolean writezmat = true;

                    if (cmd.hasOption("minnrofcohorts")) {
                        minnrvalues = Integer.parseInt(cmd.getOptionValue("minnrofcohorts")) * 10;
                    }
                    if (cmd.hasOption("minnrofsamples")) {
                        minnrsamples = Integer.parseInt(cmd.getOptionValue("minnrofsamples"));
                    }
                    String method = "pearson";
                    if (cmd.hasOption("ldmethod")) {
                        method = cmd.getOptionValue("ldmethod");
                    }

                    String in = cmd.getOptionValue("in");
                    String in2 = cmd.getOptionValue("geneannotation");
                    String out = cmd.getOptionValue("out");
                    Integer nrthreads = Integer.parseInt(cmd.getOptionValue("threads"));
                    BinaryFileLD ld = new BinaryFileLD();
                    ld.run(in, in2, nrthreads, out, writezmat, minnrsamples, minnrvalues, method);
                }


            } else if (cmd.hasOption("internalmeta")) {

                String settings = null;
                String texttoreplace = null;
                String replacewith = null;
                if (cmd.hasOption("settings")) {
                    settings = cmd.getOptionValue("settings");
                }
                if (cmd.hasOption("rt")) {
                    texttoreplace = cmd.getOptionValue("rt");
                }
                if (cmd.hasOption("rtw")) {
                    replacewith = cmd.getOptionValue("rtw");
                }
                if (settings == null) {
                    System.err.println("Error: Please provide settings with --internalmeta");
                    System.out.println();
                    printHelp();
                } else {
                    InternalMetaAnalysis bm = new InternalMetaAnalysis(settings, texttoreplace, replacewith);
                }

            } else if (cmd.hasOption("createsettingsinternalmeta")) {
                SettingsFileCreator c = new SettingsFileCreator();

                String input = null;
                if (cmd.hasOption("in")) {
                    input = cmd.getOptionValue("in");
                }
                String outputdir = null;
                if (cmd.hasOption("out")) {
                    outputdir = cmd.getOptionValue("out");
                }

                String averagingmethod = "mean";
                if (cmd.hasOption("averagingmethod")) {
                    averagingmethod = cmd.getOptionValue("averagingmethod");
                }
                String nrpermutations = "" + 10;
                if (cmd.hasOption("nrperm")) {
                    nrpermutations = cmd.getOptionValue("nrperm");
                }

                String scriptlocserver = null;
                if (cmd.hasOption("scriptlocserver")) {
                    scriptlocserver = cmd.getOptionValue("scriptlocserver");
                }
                String scriptloclocal = null;
                if (cmd.hasOption("scriptloclocal")) {
                    scriptloclocal = cmd.getOptionValue("scriptloclocal");
                }

                String tool = null;
                if (cmd.hasOption("tool")) {
                    tool = cmd.getOptionValue("tool");
                }

                if (scriptlocserver == null || scriptloclocal == null || scriptloclocal == null || tool == null) {
                    System.out.println("use --tool, --scriptlocserver, --scriptloclocal, --in and --out");
                } else {
                    c.createInternalMeta(input, outputdir, averagingmethod, nrpermutations, scriptloclocal, scriptlocserver, tool);
                }
            } else if (cmd.hasOption("applyfdr")) {

                ApplyFDR f = new ApplyFDR();
                String input = null;
                if (cmd.hasOption("in")) {
                    input = cmd.getOptionValue("in");
                }

                String output = null;
                if (cmd.hasOption("out")) {
                    output = cmd.getOptionValue("out");
                }

                String ref = null;

                if (cmd.hasOption("e")) {
                    ref = cmd.getOptionValue("e");
                }

                double threshold = 0.05;
                if (cmd.hasOption("threshold")) {
                    threshold = Double.parseDouble(cmd.getOptionValue("threshold"));
                }

                boolean onlywritesignificant = false;
                if (cmd.hasOption("onlyoutputsignificant")) {
                    onlywritesignificant = true;
                }
                if (input == null || output == null || ref == null) {
                    System.out.println("Use --in --out and --eqtls");
                } else {
                    f.apply(input, output, threshold, onlywritesignificant, ref);
                }


            } else if (cmd.hasOption("createsettingsbinarymeta")) {
                SettingsFileCreator c = new SettingsFileCreator();

                String input = null;
                if (cmd.hasOption("in")) {
                    input = cmd.getOptionValue("in");
                }

                String outputdir = null;
                if (cmd.hasOption("out")) {
                    outputdir = cmd.getOptionValue("out");
                }

                String nrpermutations = "" + 10;
                if (cmd.hasOption("nrperm")) {
                    nrpermutations = cmd.getOptionValue("nrperm");
                }

                String localoutput = null;
                if (cmd.hasOption("scriptloclocal")) {
                    localoutput = cmd.getOptionValue("scriptloclocal");
                }

                Integer nrthreads = 1;
                if (cmd.hasOption("threads")) {
                    nrthreads = Integer.parseInt(cmd.getOptionValue("threads"));
                }

                String snpannotation = null;
                if (cmd.hasOption("snpannotation")) {
                    snpannotation = cmd.getOptionValue("snpannotation");
                }

                String snplimit = null;
                if (cmd.hasOption("snpconfine")) {
                    snpannotation = cmd.getOptionValue("snpconfine");
                }

                String probelimit = null;
                if (cmd.hasOption("probeconfine")) {
                    snpannotation = cmd.getOptionValue("probeconfine");
                }

                String snpprobelimit = null;
                if (cmd.hasOption("snpprobeconfine")) {
                    snpannotation = cmd.getOptionValue("snpprobeconfine");
                }

                String geneannotation = null;
                if (cmd.hasOption("geneannotation")) {
                    geneannotation = cmd.getOptionValue("geneannotation");
                }

                int nreqtls = 5000000;
                if (cmd.hasOption("nreqtls")) {
                    nreqtls = Integer.parseInt(cmd.getOptionValue("nreqtls"));
                }


                if (input == null || outputdir == null || localoutput == null || geneannotation == null || snpannotation == null) {
                    System.out.println("use --in, --out, --scriptloclocal, ----geneannotation, --snpannotation");
                } else {
                    Integer inrpermutations = Integer.parseInt(nrpermutations);

                    c.createBinaryMeta(inrpermutations, snpannotation, snplimit, probelimit, snpprobelimit, nreqtls, geneannotation, outputdir, nrthreads, input, localoutput);
                }
            } else if (cmd.hasOption("leaveoneout")) {
                boolean r = true;
                String in = null;
                String out = null;
                if (cmd.hasOption("e")) {
                    in = cmd.getOptionValue("e");
                } else {
                    r = false;
                    System.out.println("Specify QTL file using -e");
                }
                if (cmd.hasOption("o")) {
                    out = cmd.getOptionValue("o");
                } else {
                    r = false;
                    System.out.println("Specify output file name with -o");
                }
                if (r) {
                    CompareQTLEffectSizes q = new CompareQTLEffectSizes();
                    q.leaveOneOut(in, out);
                }
            } else if (cmd.hasOption("compareeffectsize")) {
                CompareQTLEffectSizes q = new CompareQTLEffectSizes();
                String eqtlfile = null;
                String groupdefinition = null;
                String outfile = null;
                String snpannotation = null;

                if (cmd.hasOption("e")) {
                    eqtlfile = cmd.getOptionValue("e");
                }
                if (cmd.hasOption("o")) {
                    outfile = cmd.getOptionValue("o");
                }
                if (cmd.hasOption("g")) {
                    groupdefinition = cmd.getOptionValue("g");
                }

                double threshold = 0.05;
                if (cmd.hasOption("threshold")) {
                    threshold = Double.parseDouble(cmd.getOptionValue("threshold"));
                }

                boolean nonan = false;
                if (cmd.hasOption("nonan")) {
                    nonan = true;
                }

                if (cmd.hasOption("annot")) {
                    snpannotation = cmd.getOptionValue("annot");
                }

                if (eqtlfile == null || groupdefinition == null || outfile == null) {
                    System.err.println("Specify -e, -g and -o with --qc");
                    System.out.println();
                    printHelp();
                } else {
                    q.compareEffectSizes(eqtlfile,
                            groupdefinition,
                            outfile,
                            snpannotation,
                            threshold,
                            nonan,
                            true
                    );
                }

            } else if (cmd.hasOption("filtereqtl")) {
                String input = null;
                String output = null;
                String snp = null;
                String gene = null;
                String snpgene = null;

                QTLFileFilter f = new QTLFileFilter();


                if (cmd.hasOption("e")) {
                    input = cmd.getOptionValue("e");
                }
                if (cmd.hasOption("o")) {
                    output = cmd.getOptionValue("o");
                }
                int minNrOfCohorts = -1;
                if (cmd.hasOption("minnrofcohorts")) {
                    minNrOfCohorts = Integer.parseInt(cmd.getOptionValue("minnrofcohorts"));
                }
                if (cmd.hasOption("o")) {
                    output = cmd.getOptionValue("o");
                }
                if (cmd.hasOption("snpconfine")) {
                    snp = cmd.getOptionValue("snpconfine");
                }
                if (cmd.hasOption("probeconfine")) {
                    gene = cmd.getOptionValue("probeconfine");
                }
                if (cmd.hasOption("snpprobeconfine")) {
                    snpgene = cmd.getOptionValue("snpprobeconfine");
                }

                if (input == null || output == null) {
                    System.out.println("Use at least -e and -o for --filtereqtl");
                    System.out.println("Optional: --snpconfine, --probeconfine, --snpprobeconfine");
                } else {
                    f.filter(snp, gene, snpgene, input, minNrOfCohorts, output);
                }
            } else if (cmd.hasOption("zscorematcheck")) {
                String input = null;
                String output = null;
                String nrperm = null;

                if (cmd.hasOption("e")) {
                    input = cmd.getOptionValue("e");
                }
                if (cmd.hasOption("o")) {
                    output = cmd.getOptionValue("o");
                }
                if (cmd.hasOption("t")) {
                    nrperm = cmd.getOptionValue("t");
                }

                CheckZScoreMeanAndVariance v = new CheckZScoreMeanAndVariance();
                v.checkZScoreTable(input, output, Integer.parseInt(nrperm));

            } else if (cmd.hasOption("zmeanandvar")) {
                String settings = null;
                String texttoreplace = null;
                String replacewith = null;
                if (cmd.hasOption("settings")) {
                    settings = cmd.getOptionValue("settings");
                }
                if (cmd.hasOption("rt")) {
                    texttoreplace = cmd.getOptionValue("rt");
                }
                if (cmd.hasOption("rtw")) {
                    replacewith = cmd.getOptionValue("rtw");
                }
                if (settings == null) {
                    System.err.println("Error: Please provide settings with --meta");
                    System.out.println();
                    printHelp();
                } else {
                    CheckZScoreMeanAndVariance v = new CheckZScoreMeanAndVariance(settings, texttoreplace, replacewith);
                }
            } else {
                System.out.println("Choose --meta or --qc");
                printHelp();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(" ", OPTIONS);

        System.exit(-1);
    }
}
