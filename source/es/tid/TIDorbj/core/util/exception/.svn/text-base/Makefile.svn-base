#
#		Makefile
#	      (Java & IDL)
# -----------------------------------------------------------------------------------

#SUBDIRS=

#EFC_SERVICES=
#JAVA_TOOLS=

#IDL_JMODULES=

SOURCES=BAD_CONTEXT_Creator BAD_INV_ORDER_Creator BAD_OPERATION_Creator \
BAD_PARAM_Creator BAD_TYPECODE_Creator COMM_FAILURE_Creator \
DATA_CONVERSION_Creator FREE_MEM_Creator IMP_LIMIT_Creator \
INITIALIZE_Creator INTERNAL_Creator INTF_REPOS_Creator \
INVALID_TRANSACTION_Creator INV_FLAG_Creator INV_IDENT_Creator \
INV_OBJREF_Creator INV_POLICY_Creator MARSHAL_Creator \
NO_IMPLEMENT_Creator NO_MEMORY_Creator NO_PERMISSION_Creator \
NO_RESOURCES_Creator NO_RESPONSE_Creator OBJECT_NOT_EXIST_Creator \
OBJ_ADAPTER_Creator PERSIST_STORE_Creator SystemExceptionCreator \
SystemExceptionEncoder SystemExceptionNames TRANSACTION_REQUIRED_Creator \
UNKNOWN_Creator TRANSACTION_ROLLEDBACK_Creator TRANSIENT_Creator 
 

USR_CLASSPATH=$(MODULE_PATH)/orb/util/lib/util.jar:$(MODULE_PATH)/orb/idl/orb/lib/CORBA.jar

#USR_IDLJ_FLAGS=
#USR_JAVAC_FLAGS=
#USR_JAR_FLAGS=

#USR_CLASSPATH=
#USR_IDL_INCLUDES=

#SERVICE_JLIB=yes
#SERVICE_JBIN=yes
LOCAL_JLIB=exception
#GLOBAL_JBIN=
#LOCAL_JLIB=
#LOCAL_JBIN=

#USR_PRE_TARGETS=
#USR_POST_TARGETS=

#PKG=

#--------------------------------------------------------
#         	PARTE FINAL: er meollo
#--------------------------------------------------------
include $(CCM_PROJECT_PATH)/java/MakeRules/TIDJMakeRules.mk

BASIC_CLASSPATH=$(JDK_DIR)/jre/lib/rt.jar
