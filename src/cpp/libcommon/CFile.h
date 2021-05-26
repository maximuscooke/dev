#ifndef _CFILE_H_
#define _CFILE_H_

#include "CObject.h"

namespace  _COMMON_NS
{
    class CFile : public CObject
    {
        public:
            _STATIC Integer fileRemove(const char* filename);
            _STATIC Integer fileRename(const char* oldFilename, const char* newFilename);
            _STATIC Boolean fileExist(const char* filename);
            _STATIC Boolean dirExists(const char* dirPath);
            _STATIC Boolean dirCreate(const char* dirPath, Integer attribs = 0777);
    };
}
#endif