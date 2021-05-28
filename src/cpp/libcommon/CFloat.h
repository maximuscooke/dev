#ifndef _CFLOAT_H_
#define _CFLOAT_H_

#include "CObject.h"

namespace  _COMMON_NS
{
    class CFloat : public CObject
    {
        private:
            Float mValue;

        public:
            CFloat()
            {
                this->mValue = 0.0f;
            }
            
            CFloat(Float value)
            {
                this->mValue = value;
            }

            _STATIC Float getMax();
            _STATIC Float getMin();

            std::string getClassName() const _OVERRIDE;
            void log() const _OVERRIDE;
            std::string toString() const _OVERRIDE;
            Integer compare(CObject* pObj) _OVERRIDE;

            _OP_NUMERIC(CFloat, Float, mValue)
            _PROPERTY(Float, Value, mValue)
     };
}
#endif