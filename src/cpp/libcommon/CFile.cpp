#include "CFile.h"
#include <fstream>
#include <sys/stat.h>

namespace  _COMMON_NS
{
    Integer CFile::fileRemove(const char* filename)
    {
        _LOGF("Attempting to remove file <%s>", filename);

       return remove(filename);
    }

    Integer CFile::fileRename(const char* oldFilename, const char* newFilename)
    {
        _LOGF("Attempting to rename file <%s> to <%s>", oldFilename, newFilename);

        return rename(oldFilename, newFilename);
    }

    Boolean CFile::fileExist(const char* filename)
    {
        _LOGF("Checking if file <%s> exists", filename);

         std::ifstream infile(filename);

        return (Boolean)infile.good();
    }

    Boolean CFile::dirExists(const char* dirPath)
    {
        _LOGF("Checking if director <%s> exists", dirPath);

        struct stat buffer;

        return (Boolean)(stat (dirPath, &buffer) == 0);
    }

    Boolean CFile::dirCreate(const char* dirPath, Integer attribs)
    {
        if (CFile::dirExists(dirPath))
        {
            return true;
        }

        _LOGF("Creating director <%s> exists", dirPath);

        return (Boolean)(mkdir(dirPath, attribs) == 0);
    }

 


 }