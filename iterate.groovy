
def geFilePath(String root, String file){
    path = root.split('/').toList()
    path.remove(path.size()-1)
    String fReturn
    for (int i=0;i<file.count('../'); i++){
        path.remove(path.size()-1)
    }
    if (path.size() > 0)
        fReturn = path.join('/') + '/' + file.replace('../','')
    else
        fReturn = file.replace('../','')
    println(root)
    println("\t"+fReturn+"\n")
    return fReturn
}

String readFileIntoString(String filePath) {
    File file = new File(filePath)
    String fileContent = file.text
    return fileContent
}

def normalizePath(String path, String from, String to){
    return path.replace(from, to)
}

def getXmlsFromChangelogFile(String filePath, Boolean isDebugPrintEnabled = false) {
    def data = readFileIntoString(filePath)
    def matches = data =~ /(?s)file="(.*?)\"/
    if (isDebugPrintEnabled)
        matches.each { println it[1] }
    def from = '\\', to = '/'
    filePath = normalizePath(filePath, from, to)

    //recursively get logs from logs till end!
    matches.each {
        getXmlsFromChangelogFile(geFilePath(filePath, normalizePath(it[1], from, to) ), isDebugPrintEnabled)
    }
}

getXmlsFromChangelogFile('System/Schema/changelog.xml', true)

