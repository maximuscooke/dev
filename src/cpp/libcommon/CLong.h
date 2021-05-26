#ifndef _CLONG_H_
#define _CLONG_H_

#include "CObject.h"

namespace  _COMMON_NS
{
    class CLong : public CObject
    {
        private:
            Long mValue;

        public:
            CLong()
            {
                this->mValue = 0.0;
            }
            
            CLong(Long value)
            {
                this->mValue = value;
            }

            _STATIC Long getMax();
            _STATIC Long getMin();

            _OVERRIDE std::string getClassName() const { return std::string( (const char*)_QUOTE(CLong) ); }
            _OVERRIDE void log() const;
            _OVERRIDE std::string toString() const;
            _OVERRIDE Integer compare(CObject* pObj);

            _OP_NUMERIC(CLong, Long, mValue)
            _OP_NUMERIC_MOD(CLong, Long, mValue)

            _PROPERTY(Long, Value, mValue)
     };
}
#endif