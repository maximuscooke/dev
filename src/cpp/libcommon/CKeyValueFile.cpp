#include "CKeyValueFile.h"
#include "CException.h"

#define _BUFFER_SIZE 1024

#define _CREATE_KEY(buffer, key, markerBegin, markeEnd, val) sprintf(buffer, "%s%s%s%s", markerBegin, key, markeEnd, val)

namespace  _COMMON_NS
{
    std::string CKeyValueFile::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CKeyValueFile) );
    }

    void CKeyValueFile::addKeyValue(stringPtr key, std::string val)
    {
        Char buffer[_BUFFER_SIZE];

        _CREATE_KEY(buffer, key, getMarkerBegin().c_str(), getMarkerEnd().c_str(), val.c_str());

        this->addLine(buffer);
    }

    std::string CKeyValueFile::getKeyValue(std::string key)
    {
        Char buffer[_BUFFER_SIZE];

        _CREATE_KEY(buffer, key.c_str(), getMarkerBegin().c_str(), getMarkerEnd().c_str(), "");

        std::string fullKey(buffer);

        for(Index i = 0; i < getLineCount(); i++)
        {
            std::string ln = getLineAt(i);
        
            if (ln.find(fullKey) != std::string::npos)
            {
                return ln.substr(fullKey.length());
            }
        }

        throw CException(_ERR_MSG_KEY_NOT_FOUND);
    }
    
    void CKeyValueFile::removeKey(std::string key)
    {
        Char buffer[_BUFFER_SIZE];

        _CREATE_KEY(buffer, key.c_str(), getMarkerBegin().c_str(), getMarkerEnd().c_str(), "");

        std::vector<CLinePosition> results;
        do
        {
            results = findInFile(key);

            if (results.size() > 0)
            {
                removeLineAt((Index)results[0].getLineNumber());
            }
        } while (results.size() > 0);
        
    }
}
