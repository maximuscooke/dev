#ifndef _CKEYVALUEFILE_H_
#define _CKEYVALUEFILE_H_

#include "CTextFile.h"

namespace  _COMMON_NS
{
    class CKeyValueFile : public CTextFile
    {
        private:
            std::string mMarkerBegin = std::string("<|");
            std::string mMarkerEnd = std::string("|>");

        public:
            void addKeyValue(stringPtr key, std::string val);

            _INLINE void addKeyValue(stringPtr key, stringPtr val) { addKeyValue(key, std::string(val)); }
            _INLINE void addKeyValue(stringPtr key, const CString& val) { addKeyValue(key, std::string(val.getString() ) ); };
            _INLINE void addKeyValue(stringPtr key, Integer val) { addKeyValue(key, CString::convert(val)); }
            _INLINE void addKeyValue(stringPtr key, Double val) { addKeyValue(key, CString::convert(val)); }
            _INLINE void addKeyValue(stringPtr key, Float val) { addKeyValue(key, CString::convert(val)); }
            _INLINE void addKeyValue(stringPtr key, Bool val) { addKeyValue(key, (val == true ? 1 : 0) ); }

            void removeKey(std::string key);
            _INLINE void removeKey(stringPtr key) { removeKey( std::string(key) ); }
            _INLINE void removeKey(const CString& val) { removeKey( val.getString() ); }

            std::string getKeyValue(std::string key);
            
            _INLINE Integer getKeyValueInteger(stringPtr key) { return CString::parseToInteger( getKeyValue(key) ); }
            _INLINE Long getKeyValueLong(stringPtr key) { return CString::parseToLong( getKeyValue(key) ); }
            _INLINE Double getKeyValueDouble(stringPtr key) { return CString::parseToDouble( getKeyValue(key) ); }
            _INLINE Float getKeyValueFloat(stringPtr key) { return CString::parseToFloat( getKeyValue(key) ); }
            _INLINE Bool getKeyValueBoolean(stringPtr key) { return CString::parseToInteger( getKeyValue(key) ) == 1 ? true : false; }

            _INLINE std::string getKeyValue(stringPtr key) { return getKeyValue( std::string(key) ); }

            _GET_PROPERTY(std::string, MarkerBegin, mMarkerBegin)
            _GET_PROPERTY(std::string, MarkerEnd, mMarkerEnd)
            _SET_PROPERTY(std::string, MarkerBegin, mMarkerBegin)
            _SET_PROPERTY(std::string, MarkerEnd, mMarkerEnd)

            std::string getClassName() const _OVERRIDE;
    };
}
#endif